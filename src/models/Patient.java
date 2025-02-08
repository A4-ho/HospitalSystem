package src.models;

<<<<<<< Updated upstream
=======
import src.enums.SymptomsEnum;

public class Patient {
    private int id;
    private String name;
    private String surname;
    private int age;
    private String gender;
    private String email;
>>>>>>> Stashed changes

public class Patient extends src.models.User {
    private int doctorId; // Assigned doctor

    public Patient(String name, String surname, String email, String password, String role) {
        super(name, surname , email, password,role);
}}


