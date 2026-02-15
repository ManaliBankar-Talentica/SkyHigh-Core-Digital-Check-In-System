# SkyHigh Core -- Digital Check-In System

## Overview

SkyHigh Core is a Spring Boot backend service designed to power SkyHigh
Airlines' digital self-check-in experience.

The system ensures:

-   Conflict-free seat assignment
-   120-second time-bound seat holds
-   Automatic seat hold expiry (background worker)
-   Baggage weight validation
-   Payment pause and resume workflow
-   High-performance seat map access
-   Concurrency-safe seat operations

Built with:

-   Java 17
-   Spring Boot 3.x
-   PostgreSQL
-   Spring Data JPA
-   Scheduled background workers
-   JUnit 5 & Mockito for testing

------------------------------------------------------------------------

# Project Structure

    skyhigh-core/
     ├── pom.xml
     ├── README.md
     ├── PRD.md
     └── src/
         ├── main/java/com/skyhigh/core/
         ├── main/resources/
         └── test/java/com/skyhigh/core/

------------------------------------------------------------------------

# Prerequisites

Before running the project, ensure you have:

-   Java 17+ installed
-   Maven 3.8+ installed
-   PostgreSQL 14+ installed
-   An IDE (IntelliJ / VS Code / STS)

------------------------------------------------------------------------

# Database Setup (PostgreSQL)

## Step 1: Create Database

Login to PostgreSQL:

    psql -U postgres

Create database:

    CREATE DATABASE skyhigh;

## Step 2: Update Credentials

Open:

    src/main/resources/application.yml

Update if needed:

    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/skyhigh
        username: postgres
        password: postgres

------------------------------------------------------------------------

# Running the Application

## Option 1: Using Maven

From project root:

    mvn clean install
    mvn spring-boot:run

Application will start on:

    http://localhost:8080

------------------------------------------------------------------------

## Option 2: Run from IDE

1.  Import project as Maven project.
2.  Run `SkyHighCoreApplication.java`.
3.  Ensure PostgreSQL is running.

------------------------------------------------------------------------

# Background Workers

The system includes a scheduled background worker:

## Seat Expiry Scheduler

-   Runs every 10 seconds.
-   Automatically releases seats held for more than 120 seconds.
-   Ensures no seat remains locked indefinitely.

Located at:

    scheduler/SeatExpiryScheduler.java

Enabled via:

    @EnableScheduling

------------------------------------------------------------------------

# Running Tests

Run unit tests using:

    mvn test

Includes:

-   Service layer tests (SeatServiceTest)
-   Controller tests (SeatControllerTest)
-   Mockito-based mocks

------------------------------------------------------------------------

# Key APIs

## Hold Seat

POST

    /api/seats/{seatId}/hold?passengerId=101

## Seat Map (if implemented)

GET

    /api/seats/flight/{flightId}

------------------------------------------------------------------------

# Logging

Logging levels configured in:

    application.yml

Example:

    logging:
      level:
        root: INFO
        com.skyhigh.core: DEBUG

------------------------------------------------------------------------

# Performance & Concurrency Notes

-   Uses transactional boundaries for consistency
-   JPA versioning for optimistic locking
-   Designed for horizontal scaling
-   Ready for Redis caching extension

------------------------------------------------------------------------

# Troubleshooting

## Common Issues

1.  Database connection refused\
    → Ensure PostgreSQL is running.

2.  Port 8080 already in use\
    → Change port in application.yml.

3.  Tests failing\
    → Ensure dependencies downloaded correctly.

------------------------------------------------------------------------

# Future Improvements

-   Redis caching layer
-   Swagger/OpenAPI documentation
-   Docker + docker-compose setup
-   CI/CD pipeline integration
-   Distributed locking support

------------------------------------------------------------------------

# Summary

SkyHigh Core provides a scalable, reliable, and concurrency-safe backend
for airline digital check-in systems with automated seat lifecycle
management and validation workflows.
