package src.Secure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 байт соли
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Хеширование пароля с солью
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = salt + password;
            byte[] hashedBytes = digest.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хеширования пароля", e);
        }
    }

    public static boolean verifyPassword(String password, String salt, String storedHash) {
        String hashedPassword = hashPassword(password, salt);
        return hashedPassword.equals(storedHash); // Сравниваем сохраненный и новый хеш
    }
}