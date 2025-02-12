package src.models;

public class Doctor extends User {
    private String specialization;

    // Конструктор с вызовом родительского конструктора
    public Doctor(int id, String name, String surname, String email, String password, String role, String specialization) {
        super(id, name, surname, email, password, role);
        this.specialization = specialization;
    }

    // Геттер для specialty
    public String getSpecialization() {
        return specialization;
    }

    // Сеттер
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Переопределяем toString
    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", role='" + getRole() + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
