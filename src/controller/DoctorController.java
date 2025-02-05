package src.controller;

import src.controller.interfaces.IDoctorController;
import src.models.Doctor;
import src.repositories.DoctorRepository;
import src.repositories.interfaces.IDoctorRepository;

import java.util.List;

public class DoctorController implements IDoctorController {

    private final IDoctorRepository repository;

    public DoctorController(IDoctorRepository repository) {
        this.repository = repository;
    }


    @Override
    public String addDoctor(Doctor doctor) {
        boolean created = repository.create(doctor);
        return created?"✅ Doctor added successfully.":"Doctor Not Created";
    }

    @Override
    public String getDoctorById(int Id) {
        Doctor doctor = repository.getById(Id);
        return doctor!=null?"Doctor Found":"Doctor Not Found";
    }

    @Override
    public String getAllDoctors() {
        List<Doctor> doctors = repository.getAll();
        return doctors.isEmpty() ? "No doctors found. " : doctors.toString();
    }

    @Override
    public String updateDoctorRole(int id, String newRole) {
        boolean updated = repository.updateDoctorRole(id,newRole);
        return updated ? "Doctor role updated successfully!" : "Failed to update doctor role";
    }

    @Override
    public String deleteDoctor(int id) {
        boolean deleted = repository.delete(id);
        return deleted ? "Doctor deleted successfully!" : "Failed to delete doctor";
    }
}
