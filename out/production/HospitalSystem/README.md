# Hospital Management System

A simple Java-based application for managing a hospital's database, including doctors and patients records. This project demonstrates the use of JDBC for database operations, object-oriented programming principles, and a basic command-line interface.

---

## Features

- Add doctors with names, specializations, and email credentials.
- Add patients with names and contact details.
- View lists of all doctors and patients.
- Clear the database records (interactive confirmation included).
- Support for PostgreSQL database connection.

---

## Technologies Used

- **Java**: For backend logic.
- **JDBC**: For database connectivity.
- **PostgreSQL**: Relational database for data storage.

---

## Prerequisites

1. **Java SDK**: Ensure you have Java 17+ installed.
2. **PostgreSQL**: Install and set up PostgreSQL database.
3. **IDE**: (Optional) Use an IDE like IntelliJ IDEA for easier development and debugging.

---

## Configuration

Before running the program, ensure you configure the database credentials correctly.

1. Update the following values in the file where `DatabaseConnection` is instantiated:
   ```java
   DatabaseConnection db = new DatabaseConnection("localhost", "5432", "postgres", "postgres", "admin");
   ```
   Replace:
   - `localhost`: Hostname of your PostgreSQL server.
   - `5432`: Port number of your PostgreSQL server.
   - `postgres`: Database name.
   - `postgres`: Username to access the database.
   - `admin`: Password to access the database.

2. Ensure PostgreSQL is running and the database schema is set up. You may execute the following SQL to create example tables:
   ```sql
   CREATE TABLE doctors (
       id SERIAL PRIMARY KEY,
       name VARCHAR(50),
       surname VARCHAR(50),
       specialization VARCHAR(100),
       email VARCHAR(100),
       password VARCHAR(255)
   );

   CREATE TABLE patients (
       id SERIAL PRIMARY KEY,
       name VARCHAR(50),
       surname VARCHAR(50),
       email VARCHAR(100),
       password VARCHAR(255)
   );
   ```

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/hospital-management-system.git
   ```
   
2. Navigate to the project folder:
   ```bash
   cd hospital-management-system
   ```

3. Compile and run the project:
   ```bash
   javac -d out src/**/*.java
   java -cp out src.HospitalSystem
   ```

4. Interact with the menu displayed in the terminal.

---

## Menu Options

### Main Menu
You will be prompted with the following options in the terminal:

1. **Add Doctor**: Enter details for a new doctor (name, surname, specialization, email, password).
2. **Add Patient**: Enter details for a new patient (name, surname, email, password).
3. **List All Doctors**: View all doctors in the database.
4. **List All Patients**: View all patients in the database.
5. **Clear Database**: Delete all records from the database (requires confirmation).
6. **Exit**: Close the application safely.

---

