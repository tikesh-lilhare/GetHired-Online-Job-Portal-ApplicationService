## GetHired - Job Portal / Application Service
## 📌 Overview
The Application Service is a core microservice of the AI-Powered Job Portal responsible for managing job applications. It allows job seekers to apply for jobs, upload resumes, and track their application status, while enabling recruiters to view and manage applications submitted for their job postings. This microservice communicates with the User Service and Job Service using OpenFeign Clients and Eureka Service Discovery to retrieve user and job information. Authentication and authorization are handled by the API Gateway using JWT.

----

## 🚀 Features
- Apply for Jobs
- View Application Details
- View Applications by User
- View Applications by Job
- Update Application Status
- Resume URL Storage
- OpenFeign Communication
- Eureka Client Registration
- MySQL Database Integration
- Exception Handling
- Bean Validation

---
## 🏗 Microservice Architecture
<img width="1536" height="1024" alt="ChatGPT Image Jul 2, 2026, 10_47_05 PM" src="https://github.com/user-attachments/assets/0a97149e-b097-47de-998e-5ae6893c4057" />



---

## 🛠 Tech Stack
## Backend
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- OpenFeign
- Maven
## Database
- MySQL 8+
## Service Discovery
- Netflix Eureka Client
## Build Tool
- Maven

---

## 📦 Maven Dependencies
The project uses the following dependencies:
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Test
- Spring Cloud OpenFeign
- Spring Cloud Netflix Eureka Client
- MySQL Connector
- Lombok

---

## 🗄 Database Configuration

## Database Name

- application_db

## Create Database

- CREATE DATABASE application_db;
## application.properties
- spring.datasource.url=jdbc:mysql://localhost:3306/application_db

- spring.datasource.username=root

- spring.datasource.password=your_password

- spring.jpa.hibernate.ddl-auto=update

- spring.jpa.show-sql=true

- spring.jpa.properties.hibernate.format_sql=true

---
## 🔄 Service Workflow
<img width="1536" height="1024" alt="ChatGPT Image Jul 2, 2026, 10_49_51 PM" src="https://github.com/user-attachments/assets/f63b8750-fdcf-49b9-bebd-dd882611b652" />

---

## 🔄 Microservice Communication

## The Application Service communicates with:

## User Service
- Purpose:
- Fetch Applicant Details
- Validate User
  
## Job Service
- Purpose:
- Fetch Job Details
- Validate Job

## Communication Method:
- OpenFeign
- REST APIs
- Eureka Discovery

---

## 📡 REST API Endpoints
## Apply for Job
POST /applications

## Request

{
  "jobId":1,
  "userId":5,
  "resumeUrl":"https://drive.google.com/resume.pdf"
}
## Get All Applications
GET /applications
## Get Application By ID
GET /applications/{id}
## Get Applications By User
GET /applications/user/{userId}
## Get Applications By Job
GET /applications/job/{jobId}
## Update Application Status
PUT /applications/{id}
Example

{
  "status":"SHORTLISTED"
}

## Possible Status Values
- APPLIED
- SHORTLISTED
- INTERVIEW
- SELECTED
- REJECTED

---

## ▶ Running the Service
## Clone Repository
git clone https://github.com/yourusername/application-service.git

## Move into Project
cd application-service

## Build Project
mvn clean install

## Run Application
mvn spring-boot:run
