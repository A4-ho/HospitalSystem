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
    public String createPatient(Patient patient) {
        boolean created = repository.createPatient(patient);
        return created?"Patient Created":"Patient Not Created";
    }

    @Override
    public String getPatientById(int id) {
        Doctor doctor = repository.getPatientById(id);
        return doctor!=null?"Patient Found":"Patient Not Found";
    }

    @Override
    public String getAllPatients() {
        List<Patient> patients = repository.getAllPatients();
        return patients.isEmpty() ? "No patients found. " : patients.toString();
    }

    @Override
    public String updatePatientRole(int id,String newRole) {
        boolean updated = repository.updatePatientRole(id, newRole);
        return updated ? "Patient role updated successfully!" : "Failed to update patient role";
    }

    @Override
    public String deletePatient(int id) {
        boolean deleted = repository.deletePatient(id);
        return deleted ? "Patient deleted successfully!" : "Failed to delete doctor";
    }
}
