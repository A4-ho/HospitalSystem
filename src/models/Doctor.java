package src.models;

import src.enums.Role;

public class Doctor extends User {
    private String specialization;

    public Doctor(int id, String name, String surname, String email, String password, Role role, String specialization) {
        super(id, name, surname , email, password,role);
        this.specialization = specialization;
    }

    public String getSpecialization() { return specialization; }
}

