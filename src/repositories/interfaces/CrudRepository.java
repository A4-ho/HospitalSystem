package src.repositories.interfaces;

import java.util.List;

public interface CrudRepository<T> {
    void add(T entity);
    T getById(int id);
    List<T> getAll();
    void update(T entity);
    void delete(int id);
}
