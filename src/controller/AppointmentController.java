package src.controller;

import src.controller.interfaces.IAppointmentController;
import src.models.Appointment;
import src.repositories.AppointmentRepository;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentController implements IAppointmentController {
    private final AppointmentRepository appointmentRepository;

    public AppointmentController(Connection connection) {
        this.appointmentRepository = new AppointmentRepository(connection);
    }

    @Override
    public void bookAppointment(int patientId, int doctorId, LocalDate date, LocalTime time) {
        if (date.isBefore(LocalDate.now())) {
            System.out.println("❌ Cannot book an appointment in the past.");
            return;
        }

        appointmentRepository.bookAppointment(patientId, doctorId, date, time);
    }

    @Override
    public void getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("⚠ No appointments found.");
        } else {
            System.out.println("📅 All Appointments:");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }

    @Override
    public void getAppointmentsByDoctor(int doctorId) {
        List<Appointment> appointments = appointmentRepository.getAppointmentsByDoctor(doctorId);
        if (appointments.isEmpty()) {
            System.out.println("⚠ No appointments found for this doctor.");
        } else {
            System.out.println("📅 Appointments for Doctor ID " + doctorId + ":");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }

    @Override
    public void getAppointmentsByPatient(int patientId) {
        List<Appointment> appointments = appointmentRepository.getAppointmentsByPatient(patientId);
        if (appointments.isEmpty()) {
            System.out.println("⚠ No appointments found for this patient.");
        } else {
            System.out.println("📅 Appointments for Patient ID " + patientId + ":");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }
}
