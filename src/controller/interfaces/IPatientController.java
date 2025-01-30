package src.controller.interfaces;

public interface IPatientController {
    void registerPatient(String name, String surname, String email, String password, int age, int doctorId);
    void displayAllPatients();
    // Add additional patient-related operations if needed.
}
