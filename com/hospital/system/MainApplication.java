package com.hospital.system;

public class MainApplication {

    public static void main(String[] args) {
        System.out.println("Starting Hospital Management System...");

        // Initialize core modules, services, or data
        Hospital hospital = new Hospital("City Care Hospital");

        // Example startup simulation
        hospital.registerPatient("John Doe", "123-45-6789");
        hospital.addDoctor("Dr. Smith", "Cardiologist");

        // Main application logic (could expand to a menu-driven interface)
        hospital.showSummary();

        System.out.println("Hospital System is now running.");
    }
}
