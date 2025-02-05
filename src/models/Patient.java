package src.models;


public class Patient extends src.models.User {
    private int age;
    private int doctorId; // Assigned doctor

    public Patient(int id, String name, String surname, String email, String password, String role, int age, int doctorId) {
        super(id, name, surname , email, password,role,age);
        this.age = age;
        this.doctorId = doctorId;
    }

    public int getAge() { return age; }
    public int getDoctorId() { return doctorId; }
}


