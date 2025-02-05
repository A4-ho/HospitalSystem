Hospital Management System

📌 Project Overview

The Hospital Management System is a comprehensive software solution designed to streamline hospital operations and enhance patient care. This system efficiently manages appointments, patient records, and staff information, ensuring a more organized and effective healthcare environment.

✨ Features

✔ Patient Management – Record and manage patient details, including medical history and personal information.✔ Doctor Management – Maintain doctor profiles, schedules, and specializations.✔ Appointment Scheduling – Seamlessly schedule, update, and cancel appointments.✔ User Authentication – Secure login for hospital staff to ensure data privacy.

🛠 Technologies Used

Backend: Java (JDBC)

Database: PostgreSQL

Development Tools: pgAdmin, IntelliJ IDEA

Build Tool: Maven

Version Control: Git

🚀 Installation & Setup

1️⃣ Clone the Repository

 git clone https://github.com/A4-ho/HospitalSystem.git

2️⃣ Import the Project

Open the project in IntelliJ IDEA or any preferred IDE.

3️⃣ Database Configuration

Install PostgreSQL and create a database (e.g., hospital_db).

Update the connection details in DatabaseConnection.java:

private static final String URL = "jdbc:postgresql://localhost:5432/hospital_db";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";

4️⃣ Build the Project

mvn clean install

5️⃣ Run the Application

Execute MainApplication.java to start the system.

📖 Usage

1️⃣ Login as hospital staff using valid credentials.2️⃣ Manage patients and doctor records efficiently.3️⃣ Schedule or Cancel appointments as needed.4️⃣ Generate Reports to monitor hospital activities.

🤝 Contribution

Contributions are welcome! To contribute:

Fork the repository.

Make changes and ensure best coding practices.

Submit a pull request for review.

📜 License

This project is licensed under the MIT License.
