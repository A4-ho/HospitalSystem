package src.models;

import src.enums.Gender;
import src.enums.Role;
import src.enums.Specialization;

public class Doctor extends User {
    private Gender gender;
    private Specialization specialization;

    public Doctor(String email, String password, String name, String surname, Gender gender, Specialization specialization) {
        super(email, password, Role.DOCTOR, name, surname);
        this.gender = gender;
        this.specialization = specialization;
    }

    public Gender getGender() { return gender; }
    public Specialization getSpecialization() { return specialization; }

    public void setGender(Gender gender) { this.gender = gender; }
    public void setSpecialization(Specialization specialization) { this.specialization = specialization; }

    @Override
    public String toString() {
        return "Doctor{" +
                "gender=" + gender +
                ", specialization=" + specialization +
                '}';
    }
}
