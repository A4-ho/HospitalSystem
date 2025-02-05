package src.repositories.interfaces;

import java.util.List;

public interface CrudRepository<T> {
    boolean create(T entity);
    T getById(int id);
    List<T> getAll();
    boolean update(int id, T entity);
    boolean delete(int id);
}
