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
    public Appointment(int id, int patientId, int doctorId, LocalDate appointmentDate, LocalTime appointmentTime, String doctorName, String patientName) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.doctorName = doctorName;
        this.patientName = patientName;
    }


    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", doctorName='" + doctorName + '\'' +
                ", patientName='" + patientName + '\'' +
                '}';
    }

    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }

    // ✅ Добавленные геттеры:
    public String getDoctorName() { return doctorName; }
    public String getPatientName() { return patientName; }
}