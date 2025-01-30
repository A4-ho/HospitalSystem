package src.models;

import src.enums.Role;


public class Patient extends src.models.User {
    private int age;
    private int doctorId; // Assigned doctor

    public Patient(int id, String name, String surname, String email, String password,Role role, int age, int doctorId) {
        super(id, name, surname , email, password,role);
        this.age = age;
        this.doctorId = doctorId;
    }

    public int getAge() { return age; }
    public int getDoctorId() { return doctorId; }
}


