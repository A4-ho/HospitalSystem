package src.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String doctorName;
    private String patientName;

    // Constructor for basic appointments
    public Appointment(int id, int patientId, int doctorId, LocalDate appointmentDate, LocalTime appointmentTime) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    // Constructor for detailed appointments (used when fetching doctor/patient names)
    public Appointment(int id, String patientName, String doctorName, LocalDate appointmentDate, LocalTime appointmentTime) {
        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    // Getters
    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public String getDoctorName() { return doctorName; }
    public String getPatientName() { return patientName; }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", doctorName='" + doctorName + '\'' +
                ", patientName='" + patientName + '\'' + '}';
    }
}
//ghjkl;'