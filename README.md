Hospital Management System

Project Overview

The Hospital Management System is a software solution designed to streamline hospital operations and improve patient care. This system enables effective management of appointments, patient records, and staff information, contributing to a more organized and efficient healthcare environment.

Features

Patient Management: Record and manage patient details and histories.

Doctor Management: Maintain profiles and schedules of doctors.

Appointment Scheduling: Efficiently schedule, update, and cancel appointments.

User Authentication: Secure login for hospital staff.

Technologies Used

Backend: Java

Database: PostgreSQL

Development Tools: pgAdmin, IntelliJ IDEA

Build Tool: Maven

Version Control: Git

Installation and Setup

Clone the Repository:

git clone https://github.com/A4-ho/HospitalSystem.git

Import Project: Open the project in IntelliJ IDEA or any preferred IDE.

Database Configuration:

Install PostgreSQL and create a database (e.g., hospital_db).

Update the connection details in the database configuration file (DatabaseConnection.java):

private static final String URL = "jdbc:postgresql://localhost:5432/hospital_db";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";

Build the Project:

mvn clean install

Run the Application: Execute the MainApplication.java to start the system.

Usage

Login as hospital staff.

Manage patients and doctor records.

Schedule or cancel appointments.

View system reports as needed.

Contribution

Contributions are welcome! Feel free to fork the repository and submit pull requests.

License

This project is licensed under MIT License.

