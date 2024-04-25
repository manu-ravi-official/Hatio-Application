## Simple Project Management App

This application helps you manage your projects and tasks effectively. You can add projects, create tasks within those projects, mark tasks as pending or completed, and even save project summaries for future reference. Additionally, the application provides features for exporting project summaries in Markdown format and redirecting you to the associated Gist file stored in Git.

### Project Structure

This project consists of two main parts:

* **Backend:** Built using Spring Boot and Maven.
* **Frontend:** Developed with React.js.

### Prerequisites

**Before you get started, make sure you have the following installed on your system:**

* **Java Development Kit (JDK):** Version 8 or later recommended for Spring Boot. Download it from [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/).
* **Maven:** A build automation tool for Java projects. Download and install it from [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi).
* **Node.js and npm (or yarn):** Node.js is required to run the development server for the React UI. Download and install it from [https://nodejs.org/en/download](https://nodejs.org/en/download) (which also includes npm). Alternatively, you can use a package manager like yarn ([https://classic.yarnpkg.com/lang/en/](https://classic.yarnpkg.com/lang/en/)).

### Running the Application

**1. Backend (Spring Boot Maven):**

1. Open a terminal or command prompt and navigate to the root directory of your project (the one containing both the `API` folder).
2. Run the following command to build and run the Spring Boot application using the Maven wrapper:


./mvnw spring-boot:run


 it will be running in localhost:8080 port


**2. Front End (React):**


1. Open a terminal or command prompt and navigate to the root directory of your project (the one containing both the  `UI` folder).
2. Run the following command to build and run the react application:


npm i


npm run dev


Project typically runs at 


http://localhost:5173/home


 or open the deployed port in browser as like above path 
```bash
