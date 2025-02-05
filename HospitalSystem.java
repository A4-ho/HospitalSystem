import src.controller.DoctorController;
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

            while (true) {
                System.out.println("\n=== Hospital Management System ===");
                System.out.println("1. Add Doctor");
                System.out.println("2. Add Patient");
                System.out.println("3. List All Doctors");
                System.out.println("4. List All Patients");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> addDoctor(doctorController);
                    case "2" -> addPatient(patientRepository);
                    case "3" -> listAllDoctors(doctorController);
                    case "4" -> listAllPatients(patientRepository);
                    case "5" -> {
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

    private static void addDoctor(DoctorController doctorController) {
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

    private static void listAllDoctors(DoctorController doctorController) {
        System.out.println("\n--- List of Doctors ---");
        String result = doctorController.getAllDoctors();
        System.out.println(result);
    }

    private static void addPatient(PatientRepository patientRepository) {
        System.out.print("Enter Patient's Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Patient's Surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter Patient's Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Patient's Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Patient's Role: ");
        String role = scanner.nextLine();
        scanner.nextLine();
        Patient patient = new Patient( name, surname, email,password,role);
        patientRepository.addPatient(patient);
        System.out.println("✅ Patient added successfully.");
    }

    private static void listAllPatients(PatientRepository patientRepository) {
        System.out.println("\n--- List of Patients ---");
        for (Patient patient : patientRepository.getAllPatients()) {
            System.out.println(patient);
        }
    }
}
