package src.repositories;

import src.models.Doctor;
import src.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository {

    private final Connection connection;

    public DoctorRepository(Connection connection) {
        this.connection = connection;
    }

    public void addDoctor(Doctor doctor) {
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
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении доктора: " + e.getMessage());
        }
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
}