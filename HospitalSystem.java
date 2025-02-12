package src;

import src.db.DatabaseConnection;
import src.models.*;
import src.repositories.*;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class HospitalSystem {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection("localhost", "5432", "postgres", "postgres", "9865");
        Connection connection = db.getConnection();

        UserRepository userRepository = new UserRepository(connection);
        DoctorRepository doctorRepository = new DoctorRepository(connection);
        AppointmentRepository appointmentRepository = new AppointmentRepository(connection);

        Scanner scanner = new Scanner(System.in);
        System.out.println("🔹 Welcome to the Hospital Management System 🔹");

        while (true) {
            System.out.println("\n1️⃣ Register  2️⃣ Login  0️⃣ Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                registerUser(userRepository, scanner);
            } else if (choice == 2) {
                loginUser(userRepository, doctorRepository, appointmentRepository, scanner);
            } else {
                System.out.println("👋 Exiting system. Goodbye!");
                break;
            }
        }
    }

    private static void registerUser(UserRepository userRepository, Scanner scanner) {
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

        User user = new User(0, name, surname, email, password, role);
        userRepository.register(user);
    }

    private static void loginUser(UserRepository userRepository, DoctorRepository doctorRepository,
                                  AppointmentRepository appointmentRepository, Scanner scanner) {
        while (true) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = userRepository.getUserByEmail(email);

            if (user != null && user.getPassword().equals(password)) {
                System.out.println("✅ Login successful! Welcome, " + user.getName());

                switch (user.getRole().toLowerCase()) {
                    case "admin":
                        adminMenu(doctorRepository, userRepository, appointmentRepository, scanner);
                        break;
                    case "doctor":
                        doctorMenu(user.getId(), appointmentRepository, scanner);
                        break;
                    case "patient":
                        patientMenu(user.getId(), appointmentRepository, scanner);
                        break;
                    default:
                        System.out.println("❌ Invalid role! Access denied.");
                }
                return;  // Exit login loop after successful login
            } else {
                System.out.println("❌ Invalid email or password! Try again.");
                System.out.println("1️⃣ Retry  0️⃣ Back to main menu");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                if (choice == 0) return;  // Exit login if user chooses to go back
            }
        }
    }


    private static void adminMenu(DoctorRepository doctorRepository, UserRepository userRepository,
                                  AppointmentRepository appointmentRepository, Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage Doctors");
            System.out.println("2. Manage Users");
            System.out.println("3. View Appointments");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageDoctors(doctorRepository, scanner);
                    break;
                case 2:
                    manageUsers(userRepository, scanner);
                    break;
                case 3:
                    appointmentRepository.getAllAppointments().forEach(System.out::println);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void manageUsers(UserRepository userRepository, Scanner scanner) {
        while (true) {
            System.out.println("\nManage Users:");
            System.out.println("1. View Users");
            System.out.println("2. Remove User");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    List<User> users = userRepository.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("No users found.");
                    } else {
                        users.forEach(System.out::println);
                    }
                    break;
                case 2:
                    System.out.print("Enter User ID to remove: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    userRepository.removeUser(userId);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void manageDoctors(DoctorRepository doctorRepository, Scanner scanner) {
        while (true) {
            System.out.println("\nManage Doctors:");
            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctors");
            System.out.println("3. Remove Doctor");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter surname: ");
                    String surname = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter specialization: ");
                    String specialization = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    Doctor doctor = new Doctor(0, name, surname, email, password, "doctor", specialization);
                    doctorRepository.addDoctor(doctor);
                    break;
                case 2:
                    doctorRepository.getAllDoctors().forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Enter Doctor ID to remove: ");
                    int removeId = scanner.nextInt();
                    scanner.nextLine();
                    doctorRepository.removeDoctor(removeId);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void doctorMenu(int doctorId, AppointmentRepository appointmentRepository, Scanner scanner) {
        while (true) {
            System.out.println("\nDoctor Menu:");
            System.out.println("1️⃣ View Appointments");
            System.out.println("0️⃣ Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                appointmentRepository.getAppointmentsByDoctor(doctorId).forEach(System.out::println);
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void patientMenu(int patientId, AppointmentRepository appointmentRepository, Scanner scanner) {
        while (true) {
            System.out.println("\nPatient Menu:");
            System.out.println("1️⃣ View My Appointments");
            System.out.println("0️⃣ Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                appointmentRepository.getAppointmentsByPatient(patientId).forEach(System.out::println);
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
