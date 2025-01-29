package src.models;

import src.enums.Role;

public class User {
    private String email;
    private String password;
    private Role role;
    private String name;
    private String surname;

    public User(String email, String password, Role role, String name, String surname) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
    }

    // Getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public String getName() { return name; }
    public String getSurname() { return surname; }

    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
