# Banking Microservices

Banking Microservices Architecture

## 📋 Overview

This project implements a banking system using a microservices architecture. The system is built with Spring Boot and Micronaut frameworks, providing a robust and scalable solution for banking operations. The project consists of 6 microservices that handle different aspects of the banking domain, from user management to financial transactions.

## 🏛 Architecture

The application is built using the following components:

| Service | Framework | Port | Description |
|---------|-----------|------|-------------|
| Eureka Server | Spring Boot | 8761 | Service discovery and registration |
| API Gateway | Spring Boot | 8999 | Entry point for all client requests |
| User Service | Micronaut | 8090 | Manages user accounts and authentication |
| Account Service | Micronaut | 8091 | Handles bank accounts and balances |
| Transaction Service | Micronaut | 8092 | Processes financial transactions |
| Loan Service | Micronaut | 8093 | Manages loan applications and processing |

## 🛠 Technologies Used

- **Spring Boot**: For Eureka Server and API Gateway
- **Micronaut**: For domain-specific microservices
- **Spring Cloud Netflix**: For service discovery
- **Spring Cloud Gateway**: For API routing
- **JPA/Hibernate**: For database operations
- **RESTful APIs**: For inter-service communication

## 💻 Microservices Details

### 1. Eureka Server (Port: 8761)

Service discovery server that allows microservices to find and communicate with each other.

**Access the Dashboard**: http://localhost:8761/

### 2. API Gateway (Port: 8999)

Entry point for all client requests, routing them to the appropriate microservice.

**Access the Gateway**: http://localhost:8999/

### 3. User Service (Port: 8090)

Manages user information and authentication.

**API Endpoints**:
- `GET /user` - Get all users
- `GET /user/{id}` - Get user by ID
- `POST /user/save` - Create a new user
- `GET /user/findById/{id}` - Find user by ID
- `GET /user/findByEmail/{email}` - Find user by email
- `POST /user/delete/{id}` - Delete user
- `PUT /user/update/{id}` - Update user details

### 4. Account Service (Port: 8091)

Manages bank accounts and account balances.

**API Endpoints**:
- `POST /account/user/{userId}` - Create account for a user
- `GET /account/user/{userId}` - Get all accounts for a user
- `GET /account/{accountId}` - Get account by ID
- `GET /account/user/{userId}/{accountId}` - Get specific account for a user
- `DELETE /account/delete/{accountId}` - Delete account
- `GET /account/user/{userId}/{accountId}/balance` - Get account balance
- `PUT /account/{accountId}/deduct/{amount}` - Deduct balance from account
- `PUT /account/{accountId}/add/{amount}` - Add balance to account

### 5. Transaction Service (Port: 8092)

Processes financial transactions between accounts.

**API Endpoints**:
- `GET /transaction/{id}` - Get transaction by ID
- `GET /transaction/` - Get all transactions
- `POST /transaction/send` - Create a new transaction
- `POST /transaction/sendFromLoan` - Create a transaction from loan
- `GET /transaction/account/{accountId}` - Get transactions by account ID
- `GET /transaction/{accountId}/sent` - Get sent transactions
- `GET /transaction/{accountId}/received` - Get received transactions

### 6. Loan Service (Port: 8093)

Manages loan applications, approvals, and repayments.

**API Endpoints**:
- `POST /loan/apply` - Apply for a loan
- `GET /loan/{id}` - Get loan by ID
- `GET /loan/user/{id}` - Get loans by user ID
- `PUT /loan/approve/{loanId}` - Approve a loan
- `GET /loan/{loanId}/repayment` - Get repayment amount for a loan
- `PUT /loan/{loanId}/pay` - Pay a loan

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- A relational database MySQL

### Installation and Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Vin-it-9/Bank-Managment-Microservice.git
   cd Bank-Managment-Microservice
   ```

2. **Start Eureka Server**
   ```bash
   cd eureka-server
   ./mvnw spring-boot:run
   ```

3. **Start API Gateway**
   ```bash
   cd api-gateway
   ./mvnw spring-boot:run
   ```

4. **Start the domain microservices**
   ```bash
   # Start User Service
   cd user-service
   ./mvnw mn:run

   # Start Account Service
   cd account-service
   ./mvnw mn:run

   # Start Transaction Service
   cd transaction-service
   ./mvnw mn:run

   # Start Loan Service
   cd loan-service
   ./mvnw mn:run
   ```

5. **Verify the setup**
   - Access the Eureka Dashboard at http://localhost:8761/
   - Ensure all microservices are registered with Eureka
   - Test API endpoints through the API Gateway at http://localhost:8999/

## 📊 System Flow

1. **Creating a User Account**
   - Register a user through the User Service
   - Create a bank account for the user through the Account Service

2. **Making Transactions**
   - Transfer money between accounts using the Transaction Service
   - Check account balance through the Account Service

3. **Applying for Loans**
   - Submit loan application through the Loan Service
   - Approve/process loans through admin endpoints
   - Transfer loan amount to user account through Transaction Service
   - Set up repayment schedule and track payments

## 🔒 Security

Security implementations include:
- Authentication
- Secure communication between services


