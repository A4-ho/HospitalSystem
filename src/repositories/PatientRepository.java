package src.repositories;

import src.models.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository {
    private final Connection connection;

    public PatientRepository(Connection connection) {
        this.connection = connection;
    }

    public void addPatient(int userId) {
        String sql = "INSERT INTO patients (user_id) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            System.out.println("✅ Patient added successfully.");
        } catch (SQLException e) {
            System.err.println("❌ Error adding patient: " + e.getMessage());
        }
    }


    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = """
            SELECT p.id, u.id AS user_id, u.name, u.surname, u.email, u.password_hash, u.role
            FROM patients p
            JOIN users u ON p.user_id = u.id
        """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving patients: " + e.getMessage());
        }
        return patients;
    }

    public Patient getPatientByEmail(String email) {
        String query = """
            SELECT p.id, u.id AS user_id, u.name, u.surname, u.email, u.password_hash, u.role
            FROM patients p
            JOIN users u ON p.user_id = u.id
            WHERE u.email = ?
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Patient(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching patient by email: " + e.getMessage());
        }

        return null;
    }
}
