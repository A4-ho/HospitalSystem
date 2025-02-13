import src.db.DatabaseConnection;
import src.models.Doctor;
import src.models.Patient;
import src.repositories.AppointmentRepository;
import src.repositories.DoctorRepository;
import src.repositories.PatientRepository;

import java.util.regex.Pattern;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HospitalSystem {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection("localhost", "5432", "HospitalSystem", "postgres", "4995475");
        Connection connection = db.getConnection();

        if (connection != null) {
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
                System.out.println("6. ❌ Exit");
                System.out.print("🔹 Choose an option: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> addDoctor(doctorRepository);
                    case "2" -> addPatient(patientRepository);
                    case "3" -> listAllDoctors(doctorRepository);
                    case "4" -> listAllPatients(patientRepository);
                    case "5" -> bookAppointment(doctorRepository, appointmentRepository);
                    case "6" -> {
                        System.out.println("👋 Exiting system... Goodbye!");
                        db.close();
                        return;
                    }
                    default -> System.out.println("⚠️ Invalid option. Please try again.");
                }
            }
        } else {
            System.err.println("❌ Connection to the database failed!");
        }
    }


    private static void addDoctor(DoctorRepository doctorRepository) {
        String name, surname, specialization, email, password;

        // Validate Name
        while (true) {
            System.out.print("Enter Doctor's Name: ");
            name = scanner.nextLine().trim();
            if (name.matches("[A-Za-z]{2,}")) break;
            System.out.println("⚠️ Invalid name. Only letters allowed, at least 2 characters.");
        }

        // Validate Surname
        while (true) {
            System.out.print("Enter Doctor's Surname: ");
            surname = scanner.nextLine().trim();
            if (surname.matches("[A-Za-z]{2,}")) break;
            System.out.println("⚠️ Invalid surname. Only letters allowed, at least 2 characters.");
        }

        // Validate Specialization
        while (true) {
            System.out.print("Enter Doctor's Specialization: ");
            specialization = scanner.nextLine().trim();
            if (specialization.matches("[A-Za-z ]{3,}")) break;
            System.out.println("⚠️ Invalid specialization. Only letters allowed, at least 3 characters.");
        }

        // Validate Email
        while (true) {
            System.out.print("Enter Doctor's Email: ");
            email = scanner.nextLine().trim();
            if (Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) break;
            System.out.println("⚠️ Invalid email format. Example: example@mail.com");
        }

        // Validate Password
        while (true) {
            System.out.print("Enter Doctor's Password: ");
            password = scanner.nextLine().trim();
            if (password.length() >= 6) break;
            System.out.println("⚠️ Password must be at least 6 characters long.");
        }

        Doctor doctor = new Doctor(0, name, surname, email, password, "doctor", specialization);
        doctorRepository.addDoctor(doctor);
    }


    private static void listAllDoctors(DoctorRepository doctorRepository) {
        System.out.println("\n--- 👨‍⚕️ List of Doctors ---");
        List<Doctor> doctors = doctorRepository.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("⚠️ No doctors found in the system.");
        } else {
            doctors.forEach(System.out::println);  // Using forEach lambda expression
        }
    }


    private static void addPatient(PatientRepository patientRepository) {
        String name, surname, email, password, role;

        // Validate Name
        while (true) {
            System.out.print("Enter Patient's Name: ");
            name = scanner.nextLine().trim();
            if (name.matches("[A-Za-z]{2,}")) break;
            System.out.println("⚠️ Invalid name. Only letters allowed, at least 2 characters.");
        }

        // Validate Surname
        while (true) {
            System.out.print("Enter Patient's Surname: ");
            surname = scanner.nextLine().trim();
            if (surname.matches("[A-Za-z]{2,}")) break;
            System.out.println("⚠️ Invalid surname. Only letters allowed, at least 2 characters.");
        }

        // Validate Email
        while (true) {
            System.out.print("Enter Patient's Email: ");
            email = scanner.nextLine().trim();
            if (Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) break;
            System.out.println("⚠️ Invalid email format. Example: example@mail.com");
        }

        // Validate Password
        while (true) {
            System.out.print("Enter Patient's Password: ");
            password = scanner.nextLine().trim();
            if (password.length() >= 6) break;
            System.out.println("⚠️ Password must be at least 6 characters long.");
        }

        // Validate Role
        while (true) {
            System.out.print("Enter Patient's Role (should be 'patient'): ");
            role = scanner.nextLine().trim().toLowerCase();
            if (role.equals("patient")) break;
            System.out.println("⚠️ Invalid role. Role should be 'patient'.");
        }

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
            patients.forEach(System.out::println);// lambda expression
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

        // Sorting doctors alphabetically by name using lambda
        availableDoctors.sort((d1, d2) -> d1.getName().compareToIgnoreCase(d2.getName()));

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

        System.out.print("📅 Enter appointment date (YYYY-MM-DD): ");
        LocalDate date;
        try {
            date = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("⚠️ Invalid date format.");
            return;
        }

        System.out.print("⏰ Enter appointment time (HH:MM): ");
        LocalTime time;
        try {
            time = LocalTime.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("⚠️ Invalid time format.");
            return;
        }

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
}
