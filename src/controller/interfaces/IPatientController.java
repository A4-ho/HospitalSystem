package src.controller.interfaces;

import src.models.Patient;

public interface IPatientController {
    String createPatient(Patient patient);
    String getPatientById(int id);
    String getAllPatients();
    String updatePatientRole(int id,String newRole);
    String deletePatient(int id);

}
