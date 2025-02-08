package src.models;

public class Doctor extends User {
    private String specialization;

    // Constructor with ID
    public Doctor(int id, String name, String surname, String email, String password, String role, String specialization) {
        super(id, name, surname, email, password, role); // Pass id to User constructor
        this.specialization = specialization;
    }

    // Getter for specialization
    public String getSpecialization() {
        return specialization;
    }

    // Setter for specialization (if needed)
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", role=" + getRole() +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
