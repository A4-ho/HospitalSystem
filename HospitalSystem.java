
import src.models.Appointment;
import src.models.User;
import src.repositories.AppointmentRepository;
import src.repositories.DoctorRepository;
import src.repositories.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static UserRepository userRepository;
    private static AppointmentRepository appointmentRepository;
    private static DoctorRepository doctorRepository;

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hospital", "postgres", "9865")) {
            userRepository = new UserRepository(connection);
            appointmentRepository = new AppointmentRepository(connection);
            doctorRepository = new DoctorRepository(connection);

            System.out.println("\n🏥 Welcome to the Hospital Management System!");
            User user = authenticateUser();

            if (user != null) {
                switch (user.getRole().toLowerCase()) {
                    case "admin":
                        adminMenu();
                        break;
                    case "doctor":
                        doctorMenu(user);
                        break;
                    case "patient":
                        patientMenu(user);
                        break;
                    default:
                        System.out.println("❌ Unknown role!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static User authenticateUser() {
        System.out.println("1️⃣ Register\n2️⃣ Login");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            return registerUser();
        } else {
            return loginUser();
        }
    }

    private static User registerUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (admin/doctor/patient): ");
        String role = scanner.nextLine();

        User newUser = new User(name, surname, email, BCrypt.hashpw(password, BCrypt.gensalt()), role);
        userRepository.register(newUser);
        return userRepository.getUserByEmail(email);
    }

    private static User loginUser() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userRepository.authenticate(email, password);
        if (user == null) {
            System.out.println("❌ Authentication failed!");
        }
        return user;
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n🔧 Admin Panel:");
            System.out.println("1️⃣ Manage Users");
            System.out.println("2️⃣ Manage Doctors");
            System.out.println("3️⃣ View Appointments");
            System.out.println("4️⃣ Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:

                    break;
                case 2:
                    // Реализация управления докторами
                    break;
                case 3:
                    showAllAppointments();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void doctorMenu(User doctor) {
        while (true) {
            System.out.println("\n🩺 Doctor Panel:");
            System.out.println("1️⃣ View Patients");
            System.out.println("2️⃣ Manage Appointments");
            System.out.println("3️⃣ Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Реализация просмотра пациентов
                    break;
                case 2:
                    // Реализация управления записями на прием
                    break;
                case 3:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void patientMenu(User patient) {
        while (true) {
            System.out.println("\n🧑‍⚕️ Patient Panel:");
            System.out.println("1️⃣ View Appointments");
            System.out.println("2️⃣ Book Appointment");
            System.out.println("3️⃣ Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Реализация просмотра записей пациента
                    break;
                case 2:
                    // Реализация записи на прием
                    break;
                case 3:
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    private static void showAllAppointments() {
        System.out.println("\n--- 📋 List of Appointments ---");
        List<Appointment> appointments = appointmentRepository.getAllAppointments();

        if (appointments.isEmpty()) {
            System.out.println("⚠️ No appointments found in the system.");
            return;
        }

        System.out.printf("%-5s | %-20s | %-20s | %-10s | %-8s%n", "ID", "Doctor", "Patient", "Date", "Time");
        System.out.println("------------------------------------------------------------");

        for (Appointment appointment : appointments) {
            System.out.printf("%-5d | %-20s | %-20s | %-10s | %-8s%n",
                    appointment.getId(),
                    appointment.getDoctorName(),
                    appointment.getPatientName(),
                    appointment.getAppointmentDate(),
                    appointment.getAppointmentTime());
        }
    }
}
