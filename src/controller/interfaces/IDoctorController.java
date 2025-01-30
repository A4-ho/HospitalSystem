package src.controller.interfaces;

public interface IDoctorController {
    void registerDoctor(String name, String surname, String email, String password, String specialization);
    void displayAllDoctors();
    // Add additional doctor-related operations if needed.
}
