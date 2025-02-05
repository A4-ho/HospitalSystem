package src.repositories;

import src.models.Appointment;
import src.repositories.interfaces.IAppointment;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository implements IAppointment {
    private final Connection connection;

    public AppointmentRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void bookAppointment(int patientId, int doctorId, LocalDate date, LocalTime time) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            statement.setInt(2, doctorId);
            statement.setDate(3, Date.valueOf(date));
            statement.setTime(4, Time.valueOf(time));

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Appointment successfully booked!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error booking appointment: " + e.getMessage());
        }
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("appointment_date").toLocalDate(),
                        rs.getTime("appointment_time").toLocalTime()
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving appointments: " + e.getMessage());
        }
        return appointments;
    }
}
