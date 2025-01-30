import src.db.DatabaseConnection;
import src.repositories.UserRepository;
import java.sql.Connection;

public class HospitalSystem {
    public static void main(String[] args) {
        // Database credentials
        String host = "jdbc:postgresql://localhost:5432";
        String username = "postgres";
        String password = "4995475";
        String database = "HospitalSystem";

        // Create database connection
        DatabaseConnection db = new DatabaseConnection(host, username, password, database);
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
