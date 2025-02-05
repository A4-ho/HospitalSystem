package src.controller.interfaces;

import src.models.Patient;

public interface IPatientController {
    String addPatient(Patient patient);
    String getPatientById(int id);
    String getAllPatients();
    String updatePatientRole(int id,String newRole);
    String deletePatient(int id);

}
