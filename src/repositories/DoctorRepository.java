    package src.repositories;

    import src.models.Doctor;
    import src.repositories.interfaces.IDoctorRepository;

    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class DoctorRepository implements IDoctorRepository {

        private final Connection connection;

        public DoctorRepository(Connection connection) {
            this.connection = connection;
        }

        /// Not implemented
        @Override
        public List<Doctor> getBySpecialization(String specialization) {
            return List.of();
        }

        /// Create Doctor
        @Override
        public boolean create(Doctor doctor) {
            String sql = "INSERT INTO doctor (id, name, surname, email, specialization, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, doctor.getId());
                stmt.setString(2, doctor.getName());
                stmt.setString(3, doctor.getSurname());
                stmt.setString(4, doctor.getEmail());
                stmt.setString(5, doctor.getSpecialization());
                stmt.setString(6, doctor.getPassword());
                stmt.setString(7, doctor.getRole());
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println("Ошибка при добавлении доктора: " + e.getMessage());
            }
            return false;
        }

        /// Not implemented now
        @Override
        public Doctor getById(int id) {
            return null;
        }

        @Override
        public List<Doctor> getAll() {
            List<Doctor> doctors = new ArrayList<>();
            String sql = "SELECT * FROM doctor";

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    doctors.add(new Doctor(
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

        /// Not implemented now
        @Override
        public boolean update(int doctorId, Doctor doctor) {
            return false;
        }

        /// Not implemented now
        @Override
        public boolean updateDoctorRole(int doctorId, String role) {
            return false;
        }

        /// Not implemented now
        @Override
        public boolean delete(int doctorId) {
            String sql = "DELETE FROM doctor WHERE id = " + doctorId;

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println("Ошибка при удалении доктора: " + e.getMessage());
            }
            return false;
        }

    }
