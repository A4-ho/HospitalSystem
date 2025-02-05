package src.repositories;

import src.models.Patient;
import src.repositories.interfaces.IPatientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PatientRepository implements IPatientRepository {
    private final Connection connection;

    public PatientRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Patient patient) {
        String sql = "INSERT INTO patient VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(2, patient.getDoctorId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Patient getById(int patientId) {
        return null;
    }

    @Override
    public List<Patient> getAll() {
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

    /// Not implemented
    @Override
    public boolean update(int patientId, Patient patient) {
        return false;
    }

    @Override
    public boolean updateRole(int patientId, String newRole) {
        return false;
    }

    ///  Not implemented
    @Override
    public boolean delete(int patientId) {
        return false;
    }
}
