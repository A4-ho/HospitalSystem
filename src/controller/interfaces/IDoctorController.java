package src.controller.interfaces;

import src.models.Doctor;

public interface IDoctorController {
    String createDoctor(Doctor doctor);
    String getDoctorById(int id);
    String getAllDoctors();
    String updateDoctorRole(int id, String newRole);
    String deleteDoctor(int id);
}
