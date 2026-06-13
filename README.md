# Neo Ledger Digital Banking System

A secure digital banking backend application built using Spring Boot and MySQL. The system provides user authentication, account management, fund transfers, deposits, withdrawals, and transaction history using REST APIs.

## Features

* User Registration
* User Login
* JWT Authentication
* Account Creation
* Deposit Funds
* Withdraw Funds
* Transfer Funds
* Transaction History
* Swagger API Documentation
* Global Exception Handling

## Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* JWT

### Database

* MySQL

### Tools

* Maven
* Swagger OpenAPI
* Git
* GitHub

## API Endpoints

### Authentication

POST /api/auth/register

POST /api/auth/login

### Accounts

POST /api/account/create

GET /api/account/{accountNumber}

GET /api/account/user/{id}

### Transactions

POST /api/transaction/deposit

POST /api/transaction/withdraw

POST /api/transaction/transfer

GET /api/transaction/history/{account}

## Future Enhancements

* Frontend Dashboard
* Role Based Access Control
* Email Notifications
* Docker Deployment
* Cloud Deployment

## Author

Obaid Majeed Khan

LinkedIn:
https://www.linkedin.com/in/obaid-majeed-khan-b00679243/

GitHub:
https://github.com/obaid-majeed
