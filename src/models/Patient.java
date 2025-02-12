package src.models;

public class Patient extends User {
    private int userId;

    public Patient(int id, int userId, String name, String surname, String email, String password, String role) {
        super(userId, name, surname, email, password, role);
        this.userId = userId;
    }

    public int getUserId() { return userId; }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + getId() +
                ", userId=" + userId +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}
