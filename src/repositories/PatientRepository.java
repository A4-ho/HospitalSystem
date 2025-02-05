package src.repositories;

import src.models.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PatientRepository {
    private Connection connection;

    public PatientRepository(Connection connection) {
        this.connection = connection;
    }
    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patient (name, surname, email, password, role) VALUES (?,?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getSurname());
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getPassword());
            stmt.setString(5, patient.getRole());

            System.out.println("Добавление пациента: " + patient.getName() + " " + patient.getSurname());
            stmt.executeUpdate();
            System.out.println("✅ Пациент успешно добавлен!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
}
