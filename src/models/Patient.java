package src.models;

public class Patient extends User {
    private int doctorId; // Assigned doctor

    public Patient(int id, String name, String surname, String email, String password, String role, int doctorId) {
        super(id, name, surname, email, password, role); // Pass id to User constructor
        this.doctorId = doctorId;
    }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + getId() +   // Use getId() instead of accessing id directly
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", role=" + getRole() +
                ", doctorId=" + doctorId +
                '}';
    }
}
