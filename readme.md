# **E-commerce Service**

## Overview
This project is a simple E-commerce Service built using Spring Boot, following the Test-Driven Development (TDD) approach. It showcases modern backend development practices including:

- Layered architecture (Controller, Service, Repository)
- Clean and concise REST APIs
- Pagination and filtering
- Exception handling
- Validation and documentation
- Database interaction using JPA
- Unit and integration tests driven by TDD principles

## API Endpoints
The following REST APIs are available in this service:

1. Create an API to return the wish list of a customer.
2. Create an API to return the total sales amount of the current day.
3. Create an API to return the max sale day of a certain time range.
4. Create an API to return the top 5 selling items of all time (based on total sale
   amount).
5. Create an API to return the top 5 selling items of the last month (based on the
   number of sales).

## Prerequisites
Before setting up the project, ensure you have the following installed:
- **Git**: For cloning the repository.
- **Docker & Docker Compose**: For containerizing and running the application.

## Framework Used

- Spring Boot 3.2.2
- JDK 21

## Setting Up the Project

### 1. Clone the Repository

Clone this repository to your local machine using the following command:

```bash
git clone https://github.com/PialKanti/Ecommerce.git
cd Ecommerce
```

### 2. Build the Application
Once you have the project, you can either run the application directly using Docker Compose or follow the manual steps outlined below to run it locally.

### 3. Running the Application with Docker Compose
#### Build and Start the Containers
**Prerequisite:** Ensure Docker is installed and running on your system.

To build and run the application using Docker Compose, execute the following command. This will start the containers in the background:

```bash
docker compose up --build -d
```
This command does the following:
- `--build`: Forces Docker Compose to rebuild the images if there are changes in the code or configuration files.
- `-d`: Runs the containers in detached mode, so they run in the background.

Once the containers are up and running, the application will be accessible at:
 - *[http://localhost:8080](http://localhost:8080)*

\
Docker Compose will automatically start all required services, such as PostgreSQL and the application itself.

#### Stopping the Application

To stop the application without removing the containers, run:

```bash
docker compose stop
```
This will stop the containers but retain the state of the services. You can restart them later using `docker compose start`.

#### Removing Containers and Volumes
To stop and remove the containers and networks defined in your Docker Compose configuration, use:

```bash
docker compose down
```

This command will stop the containers and remove them, but will not remove the volumes. Any persistent data (such as databases) will be preserved.
\
\
If you want to remove both containers and volumes (along with any persistent data), use:

```bash
docker compose down -v
```

This will completely remove the containers and associated volumes.

## Accessing Swagger UI
Once the application is running, you can access the Swagger UI for API documentation and testing the endpoints:

- **Swagger UI:** *[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)*

This link will provide you with an interactive API interface where you can see all the available endpoints and make requests directly.

## Running Locally Without Docker Compose (Optional)
If you prefer to run the application locally without Docker, you can follow these steps:

### 1. Build the Project
You can build the project using Maven or Maven Wrapper:

```bash
./mvnw clean install -DskipTests
```

### 2. Run the Application
Once the project is built, run the application using the following command:

```bash
./mvnw spring-boot:run
```

The application will be available at *[http://localhost:8080](http://localhost:8080)*, and you can access Swagger UI at:
- **Swagger UI:** *[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)*
