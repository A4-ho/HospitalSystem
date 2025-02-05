package src.models;

public class Doctor extends User {
    private String specialization;

    // Конструктор
    public Doctor(String name, String surname, String email, String password, String role, int age, String specialization) {
        super(2,name, surname, email, password, role, age); // Передаем параметры в родительский класс
        this.specialization = specialization;
    }

    // Геттер для specialization
    public String getSpecialization() {
        return specialization;
    }

    // Сеттер для specialization (если нужен)
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
