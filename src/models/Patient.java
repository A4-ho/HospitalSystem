package src.models;


public class Patient extends src.models.User {
    private int doctorId; // Assigned doctor

    public Patient(String name, String surname, String email, String password, String role) {
        super(name, surname , email, password,role);
        this.doctorId = doctorId;
    }
    public int getDoctorId() { return doctorId; }
}


