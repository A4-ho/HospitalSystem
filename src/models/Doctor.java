package src.models;

public class Doctor extends User {
    private String specialization;

<<<<<<< Updated upstream
    // Конструктор
    public Doctor(String name, String surname, String email, String password, String role, String specialization) {
        super(name, surname, email, password, role); // Передаем параметры в родительский класс
        this.specialization = specialization;
    }

    // Геттер для specialization
    public String getSpecialization() {
        return specialization;
    }

    // Сеттер для specialization (если нужен)
    public void setSpecialization(String specialization) {
=======
public class Doctor {
    private int id;
    private String name;
<<<<<<< Updated upstream
    private String surname;
    private int gender;
    private DoctorEnum specialization;

    public Doctor(int id, String name, DoctorEnum specialization,String surname, int gender) {
=======
    private DoctorEnum specialization;


    public Doctor(int id, String name, DoctorEnum specialization) {
>>>>>>> Stashed changes
        this.id = id;
        this.name = name;
>>>>>>> Stashed changes
        this.specialization = specialization;
    }
<<<<<<< Updated upstream
=======

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
<<<<<<< Updated upstream
    public DoctorEnum getSpecialization() { return specialization; }
    public void setSpecialization(DoctorEnum specialization) { this.specialization = specialization; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public int getGender() { return gender; }
    public void setGender(int gender) { this.gender = gender; }
=======

    public DoctorEnum getSpecialization() { return specialization; }
    public void setSpecialization(DoctorEnum specialization) { this.specialization = specialization; }
>>>>>>> Stashed changes
>>>>>>> Stashed changes
}
