package src.controller.interfaces;
import src.models.User;


public interface IUserController {
    String createUser(User user);
    String getUserById(int id);
    String getAllUsers();

    String updateUserRole(int id, String newRole);

    String deleteUser(int id);
}
