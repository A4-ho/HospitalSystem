package src.repositories;

import src.db.DatabaseConnection;
import src.models.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository {

    private Connection connection;

    public DoctorRepository(Connection connection) {
        this.connection=connection;
}

    public void addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctor (name, surname, gender, email, specialization) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getSurname());
            stmt.setString(3, doctor.getGender());
            stmt.setString(4, doctor.getEmail());
            stmt.setString(5, doctor.getSpecialization());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";

        try (
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                doctors.add(new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("specialization")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }
}
