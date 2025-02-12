import src.db.DatabaseConnection;
import src.models.Doctor;
import src.models.Patient;
import src.repositories.AppointmentRepository;
import src.repositories.DoctorRepository;
import src.repositories.PatientRepository;
import src.repositories.UserRepository;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class HospitalSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String ROLE_DOCTOR = "doctor";
    private static final String ROLE_PATIENT = "patient";
    private static final String ROLE_ADMIN = "admin";
    private static String currentUserRole = "";

    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection("localhost", "5432", "postgres", "postgres", "9865");
        Connection connection = db.getConnection();

        if (connection != null) {
            System.out.println("✅ Successfully connected to the database!");
            DoctorRepository doctorRepository = new DoctorRepository(connection);
            PatientRepository patientRepository = new PatientRepository(connection);
            AppointmentRepository appointmentRepository = new AppointmentRepository(connection);
            UserRepository userRepository = new UserRepository(connection);

            while (true) {
                handleUserSession(doctorRepository, patientRepository, appointmentRepository,userRepository);
            }
        } else {
            System.err.println("❌ Connection to the database failed!");
        }

    }

    private static void handleUserSession(DoctorRepository doctorRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository, UserRepository userRepository) {
        if (currentUserRole.isEmpty()) {
            System.out.println("\n=== 🔑 Login / Register ===");
            System.out.println("1. 🔐 Login");
            System.out.println("2. 🆕 Register");
            System.out.println("3. ❌ Exit");
            System.out.print("🔹 Choose an option: ");

            switch (scanner.nextLine()) {
                case "1" -> login(doctorRepository, patientRepository);
                case "2" -> register(doctorRepository, patientRepository,userRepository);
                case "3" -> {
                    System.out.println("👋 Exiting system... Goodbye!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option. Please try again.");
            }
        } else {
            showMenu(doctorRepository, patientRepository, appointmentRepository,userRepository);
        }
    }

    private static void login(DoctorRepository doctorRepository, PatientRepository patientRepository) {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Doctor doctor = doctorRepository.getDoctorByEmail(email);
        Patient patient = patientRepository.getPatientByEmail(email);

        if (doctor != null && doctor.getPassword().equals(password)) {
            currentUserRole = ROLE_DOCTOR;
            System.out.println("✅ Logged in as Doctor.");
        } else if (patient != null && patient.getPassword().equals(password)) {
            currentUserRole = ROLE_PATIENT;
            System.out.println("✅ Logged in as Patient.");
        } else if (email.equals("admin") && password.equals("admin")) {
            currentUserRole = ROLE_ADMIN;
            System.out.println("✅ Logged in as Admin.");
        } else {
            System.out.println("❌ Invalid credentials. Try again.");
        }
    }

    private static void register(DoctorRepository doctorRepository, PatientRepository patientRepository,UserRepository userRepository) {
        System.out.print("Register as (doctor/patient): ");
        String role = scanner.nextLine();
        if (role.equalsIgnoreCase(ROLE_DOCTOR)) {
            addDoctor(doctorRepository);
        } else if (role.equalsIgnoreCase(ROLE_PATIENT)) {
            addPatient(userRepository,patientRepository);
        } else {
            System.out.println("❌ Invalid role.");
        }
    }

    private static void showMenu(DoctorRepository doctorRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository, UserRepository userRepository) {
        System.out.println("\n=== 🏥 Hospital Management System ===");
        switch (currentUserRole) {
            case ROLE_ADMIN -> {
                System.out.println("1. ➕ Add Doctor");
                System.out.println("2. ➕ Add Patient");
                System.out.println("3. 📋 List All Doctors");
                System.out.println("4. 📋 List All Patients");
                System.out.println("5. 📅 Book an Appointment");
                System.out.println("6. ❌ Logout");
            }
            case ROLE_DOCTOR -> {
                System.out.println("1. 📋 List All Patients");
                System.out.println("2. 📅 Book an Appointment");
                System.out.println("3. ❌ Logout");
            }
            case ROLE_PATIENT -> {
                System.out.println("1. 📅 Book an Appointment");
                System.out.println("2. ❌ Logout");
            }
        }

        System.out.print("🔹 Choose an option: ");
        handleMenuSelection(doctorRepository, patientRepository, appointmentRepository,userRepository);
    }

    private static void handleMenuSelection(DoctorRepository doctorRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository, UserRepository userRepository) {
        switch (scanner.nextLine()) {
            case "1" -> {
                if (currentUserRole.equals(ROLE_ADMIN)) addDoctor(doctorRepository);
                else if (currentUserRole.equals(ROLE_DOCTOR)) listAllPatients(patientRepository);
                else if (currentUserRole.equals(ROLE_PATIENT)) bookAppointment(doctorRepository, appointmentRepository);
            }
            case "2" -> {
                if (currentUserRole.equals(ROLE_ADMIN)) addPatient(userRepository,patientRepository);
                else if (currentUserRole.equals(ROLE_DOCTOR)) bookAppointment(doctorRepository, appointmentRepository);
                else if (currentUserRole.equals(ROLE_PATIENT)) logout();
            }
            case "3" -> {
                if (currentUserRole.equals(ROLE_ADMIN)) listAllDoctors(doctorRepository);
                else if (currentUserRole.equals(ROLE_DOCTOR)) logout();
            }
            case "4" -> {
                if (currentUserRole.equals(ROLE_ADMIN)) listAllPatients(patientRepository);
            }
            case "5" -> {
                if (currentUserRole.equals(ROLE_ADMIN)) bookAppointment(doctorRepository, appointmentRepository);
            }
            case "6" -> {
                if (currentUserRole.equals(ROLE_ADMIN)) logout();
            }
            default -> System.out.println("⚠️ Invalid option. Please try again.");
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

        Doctor doctor = new Doctor(0, name, surname, email, password, ROLE_DOCTOR, specialization);
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

    private static void addPatient(UserRepository userRepository, PatientRepository patientRepository) {
        System.out.print("Enter Patient's Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Patient's Surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter Patient's Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Patient's Password: ");
        String password = scanner.nextLine();

        String role = "patient"; // Роль фиксирована для пациента

        int userId = userRepository.addUser(name, surname, email, password, role);
        if (userId == -1) {
            System.err.println("❌ Failed to add user.");
            return;
        }

        patientRepository.addPatient(userId); // Добавляем пациента
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
        String specialization = scanner.nextLine();

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
            doctorChoice = Integer.parseInt(scanner.nextLine());
            if (doctorChoice < 1 || doctorChoice > availableDoctors.size()) {
                System.out.println("⚠️ Invalid doctor selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid input. Please enter a number.");
            return;
        }

        Doctor chosenDoctor = availableDoctors.get(doctorChoice - 1);
        LocalDate date = promptForDate();
        if (date == null) return;

        LocalTime time = promptForTime();
        if (time == null) return;

        System.out.print("🔹 Enter your patient ID: ");
        int patientId;
        try {
            patientId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid patient ID.");
            return;
        }

        appointmentRepository.bookAppointment(patientId, chosenDoctor.getId(), date, time);
        System.out.println("✅ Appointment booked successfully!");
    }

    private static LocalDate promptForDate() {
        System.out.print("📅 Enter appointment date (YYYY-MM-DD): ");
        try {
            return LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("⚠️ Invalid date format.");
            return null;
        }
    }

    private static LocalTime promptForTime() {
        System.out.print("⏰ Enter appointment time (HH:MM): ");
        try {
            return LocalTime.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("⚠️ Invalid time format.");
            return null;
        }
    }

    private static void logout() {
        System.out.println("👋 Logging out...");
        currentUserRole = "";
    }
}