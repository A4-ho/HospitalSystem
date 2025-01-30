package src.repositories.interfaces;

import src.models.User;

public interface IUserRepository extends CrudRepository<User> {
    User getByEmail(String email);
}
