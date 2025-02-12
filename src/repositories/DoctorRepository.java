    package src.repositories;

    import src.models.Doctor;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class DoctorRepository {

        private final Connection connection;

        public DoctorRepository(Connection connection) {
            this.connection = connection;
        }

        public int addDoctor(Doctor doctor) {
            String sql = "INSERT INTO doctor (name, surname, email, specialization, password, role) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, doctor.getName());
                stmt.setString(2, doctor.getSurname());
                stmt.setString(3, doctor.getEmail());
                stmt.setString(4, doctor.getSpecialization());
                stmt.setString(5, doctor.getPassword());
                stmt.setString(6, doctor.getRole());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id"); // Получаем id нового доктора
                    }
                }
            } catch (SQLException e) {
                System.err.println("❌ Ошибка при добавлении доктора: " + e.getMessage());
            }
            return -1; // Если не удалось вставить
        }


        public List<Doctor> getAllDoctors() {
            List<Doctor> doctors = new ArrayList<>();
            String sql = "SELECT * FROM doctor";

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    doctors.add(new Doctor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("specialization")
                    ));
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при получении списка докторов: " + e.getMessage());
            }
            return doctors;
        }

        public List<Doctor> findAvailableDoctorsBySpecialization(String specialization) {
            String sql = "SELECT * FROM doctor WHERE specialization = ?";
            List<Doctor> doctors = new ArrayList<>();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, specialization);
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    Doctor doctor = new Doctor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("specialization")
                    );
                    doctors.add(doctor);
                }
            } catch (SQLException e) {
                System.out.println("❌ Error fetching doctors: " + e.getMessage());
            }
            return doctors;
        }

        public Doctor getDoctorByEmail(String email) {
            String query = "SELECT * FROM doctors WHERE email = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return new Doctor(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            "doctor", // Hardcoded since doctors always have the "doctor" role
                            resultSet.getString("specialization")
                    );
                }
            } catch (SQLException e) {
                System.err.println("❌ Error fetching doctor by email: " + e.getMessage());
            }

            return null; // Return null if no doctor is found
        }

    }
