# Bank Account Management Application

## Overview
This is a simple Java-based application for managing bank accounts. 

The application currently supports:
- Creating new bank accounts.
- Depositing money into accounts.
- Withdrawing money from accounts.
- Viewing the transaction history for an account.

This project is intentionally kept simple and can be extended to include more features and improvements over time.

---

## Prerequisites

### Required Java Version
- **Java 17**: This project requires JDK 17 or higher.

### IDE Setup
- **Lombok**: The project makes use of the **Lombok library** to simplify boilerplate code.
    - Ensure that Lombok is installed and enabled in your IDE. For example:
        - For IntelliJ IDEA: Install the Lombok plugin and enable annotation processing in the settings.
        - For Eclipse: Install the Lombok plugin following the official documentation.

---

## Application Architecture

The application follows a **Hexagonal Architecture** to separate concerns and promote maintainability. Below is an explanation of the main layers and their roles:

### 1. **API Layer**
- The API layer currently contains only the **model classes** (DTOs).
- This layer defines the data structures used to interact with the domain layer.
- In a more complete project, this layer could be extended to include **web services** (e.g., REST APIs in a Spring Boot project).

### 2. **Domain Layer**
- The core business logic of the application resides here.
- Key components in the domain layer:
    - **Incoming Services**:
        - These services define the **contracts** between the API layer and the domain layer.
        - They are implemented within the domain layer to provide business functionality.
    - **Port Interfaces**:
        - These define the contracts between the domain layer and external systems (e.g., databases or other adapters).

### 3. **Infrastructure Layer (Infra.Adapter)**
- This layer provides the concrete implementations of the port interfaces.
- It contains components such as repositories and other adapters needed to connect the domain layer with the outside world.

---

## How to Run the Application

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd <repository-name>
2. **Buil The project**
   ```bash
   mvn clean install

## NB : This application is kept minimal for simplicity but can be improved and extended in several ways:
- Add support for active/inactive statuses for bank accounts.

- Prevent operations on inactive accounts.
- Extend the application to manage customers linked to accounts.
- Add features such as transferring money between accounts etc..
- Replace manual mapping using builders with a library such as ModelMapper or MapStruct.