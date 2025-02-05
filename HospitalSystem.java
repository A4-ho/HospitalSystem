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
        DatabaseConnection db = new DatabaseConnection("localhost", "5432", "postgres", "postgres", "admin");
        Connection connection = db.getConnection();

        if (connection != null) {
            System.out.println("✅ Successfully connected to the database!");

            DoctorRepository doctorRepository = new DoctorRepository(connection);
            PatientRepository patientRepository = new PatientRepository(connection);

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
                    case "1" -> addDoctor(doctorRepository);
                    case "2" -> addPatient(patientRepository);
                    case "3" -> listAllDoctors(doctorRepository);
                    case "4" -> listAllPatients(patientRepository);
                    case "5" -> {
                        System.out.println("Exiting system...");
                        db.close();
                        return;
                    }
                    default -> System.out.println("❌ Invalid option. Please try again.");
                }
            }
        } else {
            System.err.println("❌ Connection to the database failed!");
        }
    }

    private static void addDoctor(DoctorRepository doctorRepository) {
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

        Doctor doctor = new Doctor(name, surname, email, password, "doctor", specialization);
        doctorRepository.addDoctor(doctor);
        System.out.println("✅ Doctor added successfully.");
    }

    private static void listAllDoctors(DoctorRepository doctorRepository) {
        System.out.println("\n--- List of Doctors ---");
        for (Doctor doctor : doctorRepository.getAllDoctors()) {
            System.out.printf("Name: %s | Surname %s | Specialization: %s | Email: %s%n",
                    doctor.getName(), doctor.getSurname(), doctor.getSpecialization(), doctor.getEmail());
        }
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

        Patient patient = new Patient(name, surname, email, password, "patient");
        patientRepository.addPatient(patient);
        System.out.println("✅ Patient added successfully.");
    }

    private static void listAllPatients(PatientRepository patientRepository) {
        System.out.println("\n--- List of Patients ---");
        for (Patient patient : patientRepository.getAllPatients()) {
            System.out.printf( "Name: %s | Surname: %s | Email: %s%n",
                    patient.getName(), patient.getSurname(), patient.getEmail());
        }
    }
}
