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

    public void bookAppointment(int patientId, int doctorId, LocalDate date, LocalTime time) {
        String sql = """
        INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, doctor_name, patient_name)
        VALUES (?, ?, ?, ?, 
                (SELECT CONCAT(d.surname, ' ', d.name) FROM doctor d WHERE d.id = ?), 
                (SELECT CONCAT(p.name, ' ', p.surname) FROM patient p WHERE p.id = ?)
                
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            statement.setInt(2, doctorId);
            statement.setDate(3, Date.valueOf(date));
            statement.setTime(4, Time.valueOf(time));
            statement.setInt(5, doctorId);
            statement.setInt(6, patientId);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Appointment successfully booked with doctor and patient names!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error booking appointment: " + e.getMessage());
        }
    }




    @Override
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = """
        SELECT a.id, a.patient_id, a.appointment_date, a.appointment_time, 
               d.name AS doctor_name, d.surname AS doctor_surname, 
               p.name AS patient_name
        FROM appointment a
        JOIN doctor d ON a.doctor_id = d.id
        JOIN patient p ON a.patient_id = p.id
        WHERE a.doctor_id = ?;
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        doctorId,
                        rs.getDate("appointment_date").toLocalDate(),
                        rs.getTime("appointment_time").toLocalTime(),
                        rs.getString("doctor_name"),
                        rs.getString("patient_name")
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching appointments: " + e.getMessage());
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = """
        SELECT a.id, a.patient_id, a.doctor_id, a.appointment_date, a.appointment_time, 
               d.name AS doctor_name, 
               CONCAT(p.name, ' ', p.surname) AS patient_full_name
        FROM appointments a
        JOIN doctor d ON a.doctor_id = d.id
        JOIN patient p ON a.patient_id = p.id
    """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int patientId = resultSet.getInt("patient_id");
                int doctorId = resultSet.getInt("doctor_id");
                LocalDate appointmentDate = resultSet.getDate("appointment_date").toLocalDate();
                LocalTime appointmentTime = resultSet.getTime("appointment_time").toLocalTime();
                String doctorName = resultSet.getString("doctor_name"); // Только имя врача
                String patientFullName = resultSet.getString("patient_full_name"); // Полное имя пациента

                appointments.add(new Appointment(id, patientId, doctorId, appointmentDate, appointmentTime, doctorName, patientFullName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }


    @Override
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = """
        SELECT a.id, a.patient_id, a.doctor_id, 
               d.name AS doctor_name, d.surname AS doctor_surname, 
               a.appointment_date, a.appointment_time
        FROM appointments a
        JOIN doctor d ON a.doctor_id = d.id
        WHERE a.patient_id = ?;
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String doctorFullName = rs.getString("doctor_name") + " " + rs.getString("doctor_surname");
                Appointment appointment = new Appointment(
                        rs.getInt("id"),
                        patientId,
                        rs.getInt("doctor_id"),
                        rs.getDate("appointment_date").toLocalDate(),
                        rs.getTime("appointment_time").toLocalTime(),
                        rs.getString("doctor_name"),
                        null // No need to fetch patient name here
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving appointments by patient: " + e.getMessage());
        }
        return appointments;
    }

    private static void showAllAppointments(AppointmentRepository appointmentRepository) {
        System.out.println("\n--- 📋 List of Appointments ---");
        List<Appointment> appointments = appointmentRepository.getAllAppointments();

        if (appointments.isEmpty()) {
            System.out.println("⚠️ No appointments found in the system.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println("🆔 ID: " + appointment.getId() +
                        " | 👨‍⚕️ Doctor: " + appointment.getDoctorName() +
                        " | 🧑‍ Patient: " + appointment.getPatientName() +
                        " | 📅 Date: " + appointment.getAppointmentDate() +
                        " | ⏰ Time: " + appointment.getAppointmentTime());
            }
        }
    }

}
