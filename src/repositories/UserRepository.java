package src.repositories;

import src.Secure.PasswordHasher;
import src.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (name, surname, email, hashed_password, role) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getName());
            stmt.setString(5, user.getSurname());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("name"),
                        rs.getString("surname")
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении пользователя по email: " + e.getMessage());
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("name"),
                        rs.getString("surname")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех пользователей: " + e.getMessage());
        }
        return users;
    }

    public void register(User user) {
        String sql = "INSERT INTO users (name,surname ,email, password, role) VALUES (?,?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, PasswordHasher.hashPassword(user.getPassword(),PasswordHasher.generateSalt())); // или другой метод хеширования
            stmt.setString(5, user.getRole());
            stmt.executeUpdate();
            System.out.println("✅ User registered successfully!");
        } catch (SQLException e) {
            System.err.println("❌ Error registering user: " + e.getMessage());
        }
    }

    public User authenticate(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password"); // Пароль из БД
                String hashedInputPassword = PasswordHasher.hashPassword(password,PasswordHasher.generateSalt()); // Захешировать введенный пароль

                if (storedHashedPassword.equals(hashedInputPassword)) { // Сравнить хеши
                    return new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("name"),
                            rs.getString("surname")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Ошибка аутентификации: " + e.getMessage());
        }

        return null;
    }

    public void removeUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ User with ID " + userId + " removed successfully.");
            } else {
                System.out.println("⚠️ No user found with ID " + userId + ".");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error removing user: " + e.getMessage());
        }
    }

}
