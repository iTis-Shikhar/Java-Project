🚂 Online Reservation System (Console-Based)

A comprehensive, console-based reservation system built with core Java. This application allows users to book, manage, and cancel tickets for various modes of transport including trains, buses, and flights, featuring a robust backend with data persistence.

✨ Core Features

This project is built with a focus on solid object-oriented principles and core Java functionalities.

👤 User Authentication: Secure sign-up and login system for users.

🚅 Multi-Vehicle Support: Seamlessly handles bookings for Trains, Buses, and Flights using inheritance.

🔍 Search Functionality: Users can search for available transport options based on source and destination.

🎟️ Dynamic Booking: Book available seats with instant confirmation.

⏳ Waitlist System: If a vehicle is full, users are automatically added to a waitlist. When a confirmed ticket is cancelled, the first user on the waitlist is automatically promoted.

❌ Cancellation System: Users can easily cancel their bookings using their unique PNR/Booking ID.

📊 User Dashboard: A dedicated dashboard for logged-in users to view their booking history, check waitlist status, and manage their reservations.

💾 Data Persistence: All user, vehicle, and booking data is saved to .csv files, ensuring no data is lost when the application is closed and restarted.

⚙️ Robust Error Handling: The application gracefully handles invalid user inputs without crashing.

🛠️ Technology Stack

Language: Java

Core Concepts:

Object-Oriented Programming (Inheritance, Encapsulation)

Data Structures (ArrayList)

File I/O (java.io for reading/writing CSV files)

Exception Handling (try-catch blocks)

🚀 How to Run the Project

To run this project, you will need to have the Java Development Kit (JDK) installed on your system.

1. Clone the Repository

git clone <your-repository-url>


2. Navigate to the Source Directory
Open a terminal or command prompt and navigate to the src folder inside the project directory.

cd OnlineReservationSystem/src


3. Compile All Java Files
This command compiles all the .java files from the src directory and places the output .class files in a bin directory (which it will create).

# For Windows
javac -d ../bin main/Main.java data/DataManager.java services/*.java models/*.java

# For macOS/Linux
javac -d ../bin main/Main.java data/DataManager.java services/*.java models/*.java


4. Run the Application
Execute the main method from the Main class located in the bin directory.

# For Windows
java -cp ../bin main.Main

# For macOS/Linux
java -cp ../bin main.Main


You should now see the welcome menu for the reservation system in your terminal!

🏛️ Project Architecture

The project is structured into four distinct packages to ensure a clean and maintainable codebase, following the principles of separation of concerns.

main: Contains the Main.java class, which is the entry point of the application and handles all user-facing console interactions.

models: Holds all the data blueprints (POJOs - Plain Old Java Objects) for the application.

Vehicle.java: An abstract parent class with common properties.

Train.java, Bus.java, Flight.java: Child classes that extend the Vehicle class, demonstrating inheritance.

User.java: Represents a user account.

Booking.java: Represents a ticket reservation.

services: Contains the business logic of the application.

AuthenticationService.java: Handles all logic related to user login and sign-up.

BookingService.java: Manages the core operations of searching, booking, cancelling, and handling the waitlist.

data: Manages data persistence.

DataManager.java: A utility class responsible for reading data from .csv files when the application starts and writing data back when it closes.

The project's root also contains the data_files directory, which acts as a simple database storing all vehicle, user, and booking information.