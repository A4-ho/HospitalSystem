package src.models;

import src.enums.Gender;
import src.enums.Role;
import src.enums.Specialization;
import src.enums.Symptom;

import java.util.List;

public class Patient extends User {
    private Gender gender;
    private Specialization specialization;
    private int age;
    private List<Symptom> symptoms;  // A patient can have multiple symptoms

    public Patient(String email, String password, String name, String surname, Gender gender, Specialization specialization, int age, List<Symptom> symptoms) {
        super(email, password, Role.PATIENT, name, surname);
        this.gender = gender;
        this.specialization = specialization;
        this.age = age;
        this.symptoms = symptoms;
    }

    public Gender getGender() { return gender; }
    public Specialization getSpecialization() { return specialization; }
    public int getAge() { return age; }
    public List<Symptom> getSymptoms() { return symptoms; }

    public void setGender(Gender gender) { this.gender = gender; }
    public void setSpecialization(Specialization specialization) { this.specialization = specialization; }
    public void setAge(int age) { this.age = age; }
    public void setSymptoms(List<Symptom> symptoms) { this.symptoms = symptoms; }

    @Override
    public String toString() {
        return "Patient{" +
                "gender=" + gender +
                ", specialization=" + specialization +
                ", age=" + age +
                ", symptoms=" + symptoms +
                '}';
    }
}

