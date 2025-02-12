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
            String insertUserSQL = "INSERT INTO users (username, password_hash, role, name, surname) VALUES (?, ?, 'doctor', ?, ?) RETURNING id";
            String insertDoctorSQL = "INSERT INTO doctors (user_id, specialty) VALUES (?, ?)";

            try (Connection conn = connection;
                 PreparedStatement userStmt = conn.prepareStatement(insertUserSQL);
                 PreparedStatement doctorStmt = conn.prepareStatement(insertDoctorSQL)) {

                // Добавляем пользователя
                userStmt.setString(1, doctor.getEmail()); // username = email
                userStmt.setString(2, doctor.getPassword()); // Пароль (должен быть хеширован)
                userStmt.setString(3, doctor.getName());
                userStmt.setString(4, doctor.getSurname());

                ResultSet rs = userStmt.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt("id");

                    // Добавляем доктора
                    doctorStmt.setInt(1, userId);
                    doctorStmt.setString(2, doctor.getSpecialization());
                    doctorStmt.executeUpdate();

                    return userId;
                }
            } catch (SQLException e) {
                System.err.println("❌ Ошибка при добавлении доктора: " + e.getMessage());
            }
            return -1;
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
