CampusFind � College Lost & Found Portal
CampusFind is a web-based Lost and Found Portal designed for college campuses. It helps students report lost belongings, post found items, browse available listings, and search for missing items.
The project uses Core Java and JDBC for backend processing, MySQL for database storage, and HTML, CSS, and JavaScript for the frontend.
Features
* Report lost items.
* Report found items.
* Browse lost and found items separately.
* Search for items.
* Store item details in a MySQL database.
* Connect the frontend with the Java backend using HTTP APIs.
* Retrieve and submit data using JavaScript Fetch API.
Technologies Used
Technologys
Purpose
Java 17
Backend development
JDBC
Database connectivity
MySQL
Database management
HTML
Webpage structure
CSS
Styling and layout
JavaScript
Frontend functionality
Gson
JSON processing
Maven
Dependency management
IntelliJ IDEA
Development environment
Project Structure
CampusFind/
?
??? frontend/
?   ??? assets/
?   ??? css/
?   ??? js/
?   ??? index.html
?   ??? browse.html
?   ??? report-lost.html
?   ??? report-found.html
?
??? src/
?   ??? main/
?       ??? java/
?           ??? com/
?               ??? lostfound/
?                   ??? ApiServer.java
?                   ??? DBConnection.java
?                   ??? Item.java
?                   ??? ItemDAO.java
?                   ??? Main.java
?
??? pom.xml
??? .gitignore
??? README.md
Prerequisites
Before running CampusFind, install:
* Java JDK 17 or later
* MySQL Server
* IntelliJ IDEA
* Maven (or use the Maven wrapper if available)
Database Configuration
1. Open MySQL Workbench.
2. Create the database:
CREATE DATABASE lost_found_db;
3. Create the required database tables using the SQL schema included with your project, if provided.
4. Create a .env file in the project root.
5. Add your local database configuration:
DB_URL=jdbc:mysql://localhost:3306/lost_found_db
DB_USER=campusfind_app
DB_PASSWORD=your_mysql_password
Replace your_mysql_password with your actual MySQL password.
Important: Never upload your .env file or share your database password publicly.
How to Run
1. Clone or download this repository.
2. Open the project in IntelliJ IDEA.
3. Configure the .env file with your local MySQL credentials.
4. Make sure MySQL Server is running.
5. Load the Maven dependencies.
6. Run the Java backend using the appropriate main class.
7. Open the frontend index.html file using a local web server.
8. Use the application to report, browse, and search for items.
Learning Outcomes
Through this project, I practised:
* Core Java programming.
* JDBC database connectivity.
* MySQL database operations.
* Building HTTP APIs using Java.
* JSON data handling.
* Frontend and backend integration.
* Using Git and GitHub for version control.
Author
Sakshi Patil
GitHub: Sakshi25Patil
Project Repository
CampusFind � GitHub Repository

