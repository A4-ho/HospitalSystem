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

    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patient (name, surname, email, password, role) VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getSurname());
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getPassword());
            stmt.setString(5, patient.getRole());

            ResultSet rs = stmt.executeQuery();  // Execute and retrieve the generated ID
            if (rs.next()) {
                int generatedId = rs.getInt("id");
                patient.setId(generatedId);  // Set ID in the object
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT id, name, surname, email, password, role FROM patient";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),   // Include the ID
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        -1  // Default doctorId (you can change this logic)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }}
