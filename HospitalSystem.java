import src.db.DatabaseConnection;
import src.models.Doctor;
import src.models.Patient;
import src.repositories.AppointmentRepository;
import src.repositories.DoctorRepository;
import src.repositories.PatientRepository;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class HospitalSystem {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection("localhost", "5432", "HospitalSystem", "postgres", "4995475");
        Connection connection = db.getConnection();

        if (connection != null) {
            System.out.println("✅ Successfully connected to the database!");

            DoctorRepository doctorRepository = new DoctorRepository(connection);
            PatientRepository patientRepository = new PatientRepository(connection);
            AppointmentRepository apointmentsRepository = new AppointmentRepository(connection);

            while (true) {
                System.out.println("\n=== Hospital Management System ===");
                System.out.println("1. Add Doctor");
                System.out.println("2. List All Doctors");
                System.out.println("3. Add Patient");
                System.out.println("4. List All Patients");
                System.out.println("5. Book an Appointment");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> addDoctor(doctorRepository);
                    case "2" -> listAllDoctors(doctorRepository);
                    case "3" -> addPatient(patientRepository);
                    case "4" -> listAllPatients(patientRepository);
                    case "5" -> bookAppointment(doctorRepository,  apointmentsRepository);
                    case "6" -> {
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
            System.out.println(doctor);
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
        System.out.print("Enter Patient's Role: ");
        String role = scanner.nextLine();

        Patient patient = new Patient(name, surname, email, password, role);
        patientRepository.addPatient(patient);
        System.out.println("✅ Patient added successfully.");
    }

    private static void listAllPatients(PatientRepository patientRepository) {
        System.out.println("\n--- List of Patients ---");
        for (Patient patient : patientRepository.getAllPatients()) {
            System.out.println(patient);
        }
    }

    private static void bookAppointment(DoctorRepository doctorRepository, AppointmentRepository apointmentsRepository) {
        System.out.print("Enter the specialization of the doctor: ");
        String specialization = scanner.nextLine();

        List<Doctor> availableDoctors = doctorRepository.findAvailableDoctorsBySpecialization(specialization);
        if (availableDoctors.isEmpty()) {
            System.out.println("❌ No available doctors found.");
            return;
        }

        System.out.println("Available doctors:");
        for (int i = 0; i < availableDoctors.size(); i++) {
            System.out.println((i + 1) + ". " + availableDoctors.get(i).getName() + " " + availableDoctors.get(i).getSurname());
        }

        System.out.print("Select a doctor (by number): ");
        int doctorChoice = Integer.parseInt(scanner.nextLine());
        Doctor chosenDoctor = availableDoctors.get(doctorChoice - 1);

        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter appointment time (HH:MM): ");
        LocalTime time = LocalTime.parse(scanner.nextLine());

        System.out.print("Enter your patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());

        apointmentsRepository.bookAppointment(patientId, chosenDoctor.getId(), date, time);
        System.out.println("✅ Appointment booked successfully!");
    }
}
