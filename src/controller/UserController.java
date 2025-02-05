package src.controller;

import src.controller.interfaces.IUserController;
import src.models.User;
import src.repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {

    private final IUserRepository repository;

    public UserController(IUserRepository repository) {
        this.repository = repository;
    }


    @Override
    public String createUser(User user) {
        boolean created = repository.createUser(user);
        return created?"User Created":"User Not Created";
    }

    @Override
    public String getUserById(int Id) {
        User user = repository.getUserById(Id);
        return user!=null?"Patient Found":"Patient Not Found";
    }

    @Override
    public String getAllUsers() {
        List<User> users = repository.getAllUsers();
        return users.isEmpty() ? "No users found. " : users.toString();
    }

    @Override
    public String updateUserRole(int id, String newRole) {
        boolean updated = repository.updateUserRole(id, newRole);
        return updated ? "User role updated successfully!" : "Failed to update user role";
    }

    @Override
    public String deleteUser(int id) {
        boolean deleted = repository.deleteUser(id);
        return deleted ? "User deleted successfully!" : "Failed to delete user";
    }
}
