package src.repositories.interfaces;

import src.models.Doctor;
import java.util.List;

public interface IDoctorRepository extends CrudRepository<Doctor> {
    List<Doctor> getBySpecialization(String specialization);

    /// Create interface
    @Override
    boolean create(Doctor doctor);

    /// Read interface
    @Override
    Doctor getById(int doctorId);

    @Override
    List<Doctor> getAll();

    /// Update interface
    @Override
    boolean update(int doctorId, Doctor doctor);

    boolean updateDoctorRole(int doctorId, String role);

    /// Delete interface
    @Override
    boolean delete(int doctorId);
}
