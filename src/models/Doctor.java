package src.models;

public class Doctor {
    private int id;
    private String name;
    private String surname;
    private int gender;
    private String specialization;

    public Doctor(int id, String name, String specialization,String surname, int gender) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.surname = surname;
        this.gender = gender;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public int getGender() { return gender; }
    public void setGender(int gender) { this.gender = gender; }
}
