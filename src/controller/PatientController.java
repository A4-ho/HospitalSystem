package src.controller;

import src.controller.interfaces.IPatientController;
import src.models.Doctor;
import src.models.Patient;
import src.repositories.interfaces.IDoctorRepository;
import src.repositories.interfaces.IPatientRepository;

import java.util.List;

public class PatientController implements IPatientController {
    private final IPatientRepository repository;

    public PatientController(IPatientRepository repository) {
        this.repository = repository;
    }


    @Override
    public String addPatient(Patient patient) {
        boolean created = repository.create(patient);
        return created?"✅ Patient added successfully.":"Patient Not Created";
    }

    @Override
    public String getPatientById(int id) {
        Patient patient = repository.getById(id);
        return patient!=null?"Patient Found":"Patient Not Found";
    }

    @Override
    public String getAllPatients() {
        List<Patient> patients = repository.getAll();
        return patients.isEmpty() ? "No patients found. " : patients.toString();
    }

    @Override
    public String updatePatientRole(int id,String newRole) {
        boolean updated = repository.updateRole(id, newRole);
        return updated ? "Patient role updated successfully!" : "Failed to update patient role";
    }

    @Override
    public String deletePatient(int id) {
        boolean deleted = repository.delete(id);
        return deleted ? "Patient deleted successfully!" : "Failed to delete doctor";
    }
}
