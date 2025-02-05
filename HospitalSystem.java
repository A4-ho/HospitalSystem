import src.db.DatabaseConnection;
import src.repositories.UserRepository;
import java.sql.Connection;

public class HospitalSystem {
    public static void main(String[] args) {
        // Create database connection using correct credentials
        DatabaseConnection db = new DatabaseConnection("your host", "your port", "your db", "your username", "your password");
        Connection connection = db.getConnection();

        if (connection != null) {
            System.out.println("Successfully connected to the database!");

            // Example: Create UserRepository and fetch users
            UserRepository userRepo = new UserRepository(connection);
            System.out.println("Users in the system: " + userRepo.getAllUsers());


            db.close();
        } else {
            System.out.println("❌ Connection failed!");
        }



    }
}
