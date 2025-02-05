package src.repositories.interfaces;

import src.models.Doctor;
import src.models.Patient;

import java.util.List;

public interface IPatientRepository extends CrudRepository<Patient> {
    @Override
    Patient getById(int id);

    boolean createPatient(Patient patient);

    Doctor getPatientById(Object id);

    List<Patient> getAllPatients();

    boolean deletePatient(int id);

    boolean updatePatientRole(Object id, Object newRole);
}
