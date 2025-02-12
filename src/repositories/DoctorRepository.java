package src.repositories;

import src.models.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DoctorRepository {
    private static final Logger logger = Logger.getLogger(DoctorRepository.class.getName());
    private final Connection connection;

    public DoctorRepository(Connection connection) {
        this.connection = connection;
    }

    // Добавление доктора (с автоинкрементным id)
    public int addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctor (name, surname, email, specialization, password, role) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getSurname());
            stmt.setString(3, doctor.getEmail());
            stmt.setString(4, doctor.getSpecialization());
            stmt.setString(5, doctor.getPassword());
            stmt.setString(6, doctor.getRole());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Ошибка: доктор не был добавлен.");
            }

            // Получаем сгенерированный ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Ошибка: не удалось получить ID нового доктора.");
                }
            }
        } catch (SQLException e) {
            logger.severe("Ошибка при добавлении доктора: " + e.getMessage());
            return -1;
        }
    }

    // Получение всех докторов
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";

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
            logger.severe("Ошибка при получении списка докторов: " + e.getMessage());
        }
        return doctors;
    }

    // Поиск докторов по специализации
    public List<Doctor> findAvailableDoctorsBySpecialization(String specialization) {
        String sql = "SELECT * FROM doctor WHERE specialization = ?";
        List<Doctor> doctors = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, specialization);
            ResultSet rs = statement.executeQuery();

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
            logger.severe("Ошибка при поиске докторов: " + e.getMessage());
        }
        return doctors;
    }
}

//212121
