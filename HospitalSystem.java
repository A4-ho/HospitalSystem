import src.db.DatabaseConnection;
import src.repositories.UserRepository;
import java.sql.Connection;

public class HospitalSystem {
    public static void main(String[] args) {
        // Database credentials
        String host = "jdbc:postgresql://localhost:5432/HospitalSystem"; // Correct host URL
        String username = "postgres"; // Your PostgreSQL username
        String password = "admin";   // Your PostgreSQL password

        // Create database connection
        String postgres = "",admin = "";
        DatabaseConnection db = new DatabaseConnection(5432,postgres,admin);
        Connection connection = db.getConnection();

        if (connection != null) {
            System.out.println("Successfully connected to the database!");

            // Example: Create UserRepository and fetch users
            UserRepository userRepo = new UserRepository(connection);
            System.out.println("Users in the system: " + userRepo.getAllUsers());

            // Close the connection when done
            db.close();
        } else {
            System.out.println("❌ Connection failed!");
        }
    }
}
