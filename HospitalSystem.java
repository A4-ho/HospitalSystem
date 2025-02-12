import src.db.DatabaseConnection;
import src.models.Appointment;
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

        if (connection == null) {
            System.err.println("❌ Connection to the database failed!");
            return;
        }

        System.out.println("✅ Successfully connected to the database!");

        DoctorRepository doctorRepository = new DoctorRepository(connection);
        PatientRepository patientRepository = new PatientRepository(connection);
        AppointmentRepository appointmentRepository = new AppointmentRepository(connection);

        while (true) {
            System.out.println("\n=== 🏥 Hospital Management System ===");
            System.out.println("1. ➕ Add Doctor");
            System.out.println("2. ➕ Add Patient");
            System.out.println("3. 📋 List All Doctors");
            System.out.println("4. 📋 List All Patients");
            System.out.println("5. 📅 Book an Appointment");
            System.out.println("6. 📅 Show all Appointments");
            System.out.println("7. ❌ Exit");
            System.out.print("🔹 Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addDoctor(doctorRepository);
                case "2" -> addPatient(patientRepository);
                case "3" -> listAllDoctors(doctorRepository);
                case "4" -> listAllPatients(patientRepository);
                case "5" -> bookAppointment(doctorRepository, appointmentRepository);
                case "6" -> showAppointmentsMenu(appointmentRepository, doctorRepository);
                case "7" -> {
                    System.out.println("👋 Exiting system... Goodbye!");
                    db.close();
                    return;
                }
                default -> System.out.println("⚠️ Invalid option. Please try again.");
            }
        }
    }

    private static void addDoctor(DoctorRepository doctorRepository) {
        System.out.print("Enter Doctor's Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Doctor's Surname: ");
        String surname = scanner.nextLine().trim();
        System.out.print("Enter Doctor's Specialization: ");
        String specialization = scanner.nextLine().trim();
        System.out.print("Enter Doctor's Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter Doctor's Password: ");
        String password = scanner.nextLine().trim();

        Doctor doctor = new Doctor(0, name, surname, email, password, "doctor", specialization);
        doctorRepository.addDoctor(doctor);
        System.out.println("✅ Doctor added successfully.");
    }

    private static void listAllDoctors(DoctorRepository doctorRepository) {
        System.out.println("\n--- 👨‍⚕️ List of Doctors ---");
        List<Doctor> doctors = doctorRepository.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("⚠️ No doctors found in the system.");
        } else {
            doctors.forEach(System.out::println);
        }
    }

    private static void addPatient(PatientRepository patientRepository) {
        System.out.print("Enter Patient's Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Patient's Surname: ");
        String surname = scanner.nextLine().trim();
        System.out.print("Enter Patient's Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter Patient's Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Enter Patient's Role: ");
        String role = scanner.nextLine().trim();

        Patient patient = new Patient(0, name, surname, email, password, role, -1);
        patientRepository.addPatient(patient);

        System.out.println("✅ Patient added successfully.");
    }

    private static void listAllPatients(PatientRepository patientRepository) {
        System.out.println("\n--- 🏥 List of Patients ---");
        List<Patient> patients = patientRepository.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("⚠️ No patients found in the system.");
        } else {
            patients.forEach(System.out::println);
        }
    }

    private static void bookAppointment(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        System.out.print("Enter the specialization of the doctor: ");
        String specialization = scanner.nextLine().trim();

        List<Doctor> availableDoctors = doctorRepository.findAvailableDoctorsBySpecialization(specialization);
        if (availableDoctors.isEmpty()) {
            System.out.println("❌ No available doctors found.");
            return;
        }

        System.out.println("\n📋 Available doctors:");
        for (int i = 0; i < availableDoctors.size(); i++) {
            System.out.println((i + 1) + ". " + availableDoctors.get(i).getName() + " " + availableDoctors.get(i).getSurname());
        }

        System.out.print("🔹 Select a doctor (by number): ");
        int doctorChoice;
        try {
            doctorChoice = Integer.parseInt(scanner.nextLine().trim());
            if (doctorChoice < 1 || doctorChoice > availableDoctors.size()) {
                System.out.println("⚠️ Invalid doctor selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid input. Please enter a number.");
            return;
        }

        Doctor chosenDoctor = availableDoctors.get(doctorChoice - 1);

        System.out.print("📅 Enter appointment date (YYYY-MM-DD): ");
        LocalDate date;
        try {
            date = LocalDate.parse(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("⚠️ Invalid date format.");
            return;
        }

        System.out.print("⏰ Enter appointment time (HH:MM): ");
        LocalTime time;
        try {
            time = LocalTime.parse(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("⚠️ Invalid time format.");
            return;
        }

        System.out.print("🔹 Enter your patient ID: ");
        int patientId;
        try {
            patientId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid patient ID.");
            return;
        }

        appointmentRepository.bookAppointment(patientId, chosenDoctor.getId(), date, time);
        System.out.println("✅ Appointment booked successfully!");
    }

    public static void showAppointmentsMenu(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        System.out.print("Enter your patient ID: ");
        int patientId;
        try {
            patientId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid patient ID format.");
            return;
        }

        List<Appointment> appointments = appointmentRepository.getAppointmentsByPatient(patientId);

        if (appointments.isEmpty()) {
            System.out.println("⚠️ You have no scheduled appointments.");
            return;
        }

        System.out.println("\n📅 Your Appointments:");
        for (Appointment a : appointments) {
            System.out.println("🔍 Looking up doctor with ID: " + a.getDoctorId());  // Debugging line
            Doctor doctor = doctorRepository.getDoctorById(a.getDoctorId()); // Fetch doctor details

            String doctorName = (doctor != null) ? doctor.getName() + " " + doctor.getSurname() : "Unknown Doctor";

            System.out.println("👨‍⚕️ Doctor: " + doctorName +
                    " | 📅 Date: " + a.getAppointmentDate() +
                    " | ⏰ Time: " + a.getAppointmentTime());
        }
    }}
