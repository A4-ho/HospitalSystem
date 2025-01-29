package src.models;

public class Patient {
    private int id;
    private String name;
    private String surname;
    private int age;
    private String gender;
    private String email;

    public Patient(int id, String name, int age, String gender, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname; 
        this.age = age;
        this.gender = gender;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() {return surname; }
    public void setSurname(String surname) {this.surname = surname;} 
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getEmail() { return email; }
    public void setEmail() { this.email = email; } 
   
}
