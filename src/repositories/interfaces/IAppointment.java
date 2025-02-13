package src.repositories.interfaces;

import src.models.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IAppointment {
    void bookAppointment(int patientId, int doctorId, LocalDate date, LocalTime time);
    List<Appointment> getAppointmentsByPatient(int patientId);
    List<Appointment> getAppointmentsByDoctor(int doctorId);  // ✅ Add this method
    List<Appointment> getAllAppointments();  // ✅ Add this method
}
