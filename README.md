## JAVA-APPLICATION-FACEBOOK-CLONE

**Java GUI Application Suite**

## Description

This project is a collection of Java-based GUI applications that includes a user registration form, a login interface, and a basic calculator. Each application provides essential functionality for managing user interactions and performing basic arithmetic operations.

## Features

- **Create Form**: Allows users to register or input data.
- **Login Interface**: Authenticates users based on their credentials.
- **Post-Login Dashboard**: Manages user interactions post-login.
- **GUI Calculator**: A simple calculator with basic arithmetic operations.

## Requirements

To run this project, you'll need the following dependencies and tools installed on your system:

- **Java Development Kit (JDK) 8 or higher**
- **MySQL Database**
- **Any IDE that supports Java (e.g., IntelliJ IDEA, Eclipse, NetBeans)**
- **Basic knowledge of Java and Swing for GUI applications**

## Installation and Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/sachinchaunal/JAVA-APPLICATION-FACEBOOK-CLONE.git
   cd JAVA-APPLICATION-FACEBOOK-CLONE
   ```

2. **Open the Project in Your IDE**:
   - Open your preferred IDE and import the project.

3. **Database Configuration**:
   - Ensure you have MySQL installed on your system.
   - Create the database and required tables by executing the following SQL script:

     ```sql
     -- Create the database
     CREATE DATABASE IF NOT EXISTS facethebook;
     USE facethebook;

     -- Create the user_details table
     CREATE TABLE IF NOT EXISTS user_details (
         usr_id INT AUTO_INCREMENT PRIMARY KEY,
         first_name VARCHAR(100) NOT NULL,
         surname VARCHAR(100) NOT NULL,
         mobileno VARCHAR(15) UNIQUE NOT NULL,
         email VARCHAR(255) UNIQUE NOT NULL
     );

     -- Create the authentication table
     CREATE TABLE IF NOT EXISTS authentication (
         auth_id INT AUTO_INCREMENT PRIMARY KEY,
         usr_id INT NOT NULL,
         password VARCHAR(255) NOT NULL,
         FOREIGN KEY (usr_id) REFERENCES user_details(usr_id) ON DELETE CASCADE
     );
     ```

   - Update the database connection details in the Java files if necessary (e.g., username, password).

4. **Running the Application**:
   - Locate the `main` method in this class (`Front_end_login.java`).
   - Run the main method to start the application.

## Usage

- **Create Form**: Run `Create_Form.java` to open the registration form.
- **Login**: Run `Front_end_login.java` to access the login interface.
- **Calculator**: Run `RealGUIcalculator.java` to use the GUI calculator.

