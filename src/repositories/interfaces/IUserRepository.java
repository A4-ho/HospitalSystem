package src.repositories.interfaces;

import src.models.Doctor;
import src.models.User;

import java.util.List;

public interface IUserRepository extends CrudRepository<User> {
    User getByEmail(String email);
    boolean createUser(User user);
    boolean updateUserRole(int id,String newRole);
    User getUserById(int Id);
    List<User> getAllUsers();
    boolean deleteUser(int id);
}
