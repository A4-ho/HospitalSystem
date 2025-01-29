package src;


import src.db.DatabaseConnection;

public class HospitalManagement {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection("localhost", "user", "pass", "mydb");



        db.close();
    }
}
