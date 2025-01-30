package src.repositories.interfaces;

import src.models.Doctor;
import java.util.List;

public interface IDoctorRepository extends CrudRepository<Doctor> {
    List<Doctor> getBySpecialization(String specialization);
}
