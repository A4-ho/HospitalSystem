import src.controller.DoctorController;
import src.controller.PatientController;
import src.controller.interfaces.IDoctorController;
import src.controller.interfaces.IPatientController;
import src.db.DatabaseConnection;
import src.models.Doctor;
import src.models.Patient;
import src.repositories.DoctorRepository;
import src.repositories.PatientRepository;

import java.sql.Connection;
import java.util.Scanner;

public class HospitalSystem {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Create database connection using correct credentials
        DatabaseConnection db = new DatabaseConnection("localhost", "5432", "postgres", "postgres", "11qwertyuiop");
        Connection connection = db.getConnection();

        if (connection != null) {
            System.out.println("✅ Successfully connected to the database!");

            DoctorRepository doctorRepository = new DoctorRepository(connection);
            PatientRepository patientRepository = new PatientRepository(connection);

            DoctorController doctorController = new DoctorController(doctorRepository);
            PatientController patientController = new PatientController(patientRepository);

            while (true) {
                System.out.println("\n=== Hospital Management System ===");
                System.out.println("1. Add Doctor");
                System.out.println("2. Add Patient");
                System.out.println("3. List All Doctors");
                System.out.println("4. List All Patients");
                System.out.println("5. Delete Doctor");
                System.out.println("6. Delete Patient");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> addDoctor(doctorController);
                    case "2" -> addPatient(patientController);
                    case "3" -> listAllDoctors(doctorController);
                    case "4" -> listAllPatients(patientController);
                    case "5" -> deleteDoctor(doctorController);
                    case "6" -> deletePatient(patientController);
                    case "7" -> {
                        System.out.println("Exiting system...");
                        db.close();
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.err.println("❌ Connection to the database failed!");
        }
    }

    private static void addDoctor(IDoctorController doctorController) {
        System.out.print("Enter Doctor's Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Doctor's Surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter Doctor's Specialization: ");
        String specialization = scanner.nextLine();
        System.out.print("Enter Doctor's Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Doctor's Password: ");
        String password = scanner.nextLine();

        // Assuming 'id' is auto-incremented and role is predefined as 'doctor'
        Doctor doctor = new Doctor( name, surname, email, password, "doctor", specialization);
        String result = doctorController.addDoctor(doctor);
        System.out.println(result);
    }

    private static void listAllDoctors(IDoctorController doctorController) {
        System.out.println("\n--- List of Doctors ---");
        String result = doctorController.getAllDoctors();
        System.out.println(result);
    }

    private static void addPatient(IPatientController patientController) {
        System.out.print("Enter Patient's Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Patient's Surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter Patient's Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Patient's Password: ");
        String password = scanner.nextLine();
        scanner.nextLine();
        Patient patient = new Patient( name, surname, email,password, "patient");
        String result = patientController.addPatient(patient);
        System.out.println(result);
    }

    private static void listAllPatients(IPatientController patientController) {
        System.out.println("\n--- List of Patients ---");
        String result = patientController.getAllPatients();
        System.out.println(result);
    }

    private static void deleteDoctor(IDoctorController doctorController) {
        listAllDoctors(doctorController);
        System.out.print("Enter doctor's id for deleting: ");
        int id = scanner.nextInt();
        String result = doctorController.deleteDoctor(id);
        System.out.println(result);
    }

    private static void deletePatient(IPatientController patientController) {
        listAllPatients(patientController);
        System.out.print("Enter patient's id for deleting: ");
        int id = scanner.nextInt();
        String result = patientController.deletePatient(id);
        System.out.println(result);
    }
}
