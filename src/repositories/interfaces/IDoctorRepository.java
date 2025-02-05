package src.repositories.interfaces;

import src.models.Doctor;
import java.util.List;

public interface IDoctorRepository extends CrudRepository<Doctor> {
    List<Doctor> getBySpecialization(String specialization);
    boolean createDoctor(Doctor doctor);
    Doctor getDoctorById(int id);
    List<Doctor> getAllDoctors();
    boolean updateDoctorRole(int id, String role);
    boolean deleteDoctor(int id);
}
