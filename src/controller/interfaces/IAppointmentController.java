package src.controller.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IAppointmentController {
    void bookAppointment(int patientId, int doctorId, LocalDate date, LocalTime time);
    void getAllAppointments();
    void getAppointmentsByDoctor(int doctorId);
    void getAppointmentsByPatient(int patientId);
}

