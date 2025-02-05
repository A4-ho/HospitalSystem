package src.repositories.interfaces;

import src.models.Doctor;
import src.models.Patient;

import java.util.List;

public interface IPatientRepository extends CrudRepository<Patient> {
    boolean create(Patient patient);
    Patient getById(int patientId);
    List<Patient> getAll();
    boolean update(int patientId, Patient patient);
    boolean updateRole(int patientId, String newRole);
    boolean delete(int patientId);
}
