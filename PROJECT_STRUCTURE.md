# SkyHigh Core -- Project Structure Documentation

## Overview

This document explains the folder structure, key modules, and the
purpose of each component in the SkyHigh Core project.

The project follows a standard layered Spring Boot architecture with
clear separation of concerns.

------------------------------------------------------------------------

# Root Directory

    skyhigh-core/
     ├── pom.xml
     ├── README.md
     ├── PRD.md
     ├── PROJECT_STRUCTURE.md
     └── src/

### pom.xml

-   Maven configuration file.
-   Manages dependencies (Spring Boot, JPA, PostgreSQL, Testing).
-   Defines build lifecycle and plugins.

### README.md

-   Setup instructions.
-   How to run the application.
-   Background worker explanation.

### PRD.md

-   Product Requirements Document.
-   Business goals, functional requirements, NFRs.

------------------------------------------------------------------------

# src/main/java/com/skyhigh/core

This is the main application source directory.

    src/main/java/com/skyhigh/core/

Contains all application logic.

------------------------------------------------------------------------

## 1. Application Entry Point

### SkyHighCoreApplication.java

-   Main Spring Boot entry class.
-   Bootstraps the application.
-   Starts embedded Tomcat server.

------------------------------------------------------------------------

## 2. controller/

Handles incoming HTTP requests.

### Purpose

-   Exposes REST APIs.
-   Validates request input.
-   Delegates business logic to services.

### Example

-   SeatController
    -   Hold seat endpoint
    -   Seat map retrieval

Controllers should contain minimal logic.

------------------------------------------------------------------------

## 3. service/

Contains business logic.

### Purpose

-   Implements core system behavior.
-   Manages seat lifecycle transitions.
-   Handles concurrency and validations.
-   Coordinates between repositories and external services.

### Example

-   SeatService
    -   Hold seat
    -   Confirm seat
    -   Enforce 120-second rule

Services are transactional and enforce business rules.

------------------------------------------------------------------------

## 4. repository/

Data access layer.

### Purpose

-   Communicates with PostgreSQL database.
-   Uses Spring Data JPA.
-   Abstracts persistence operations.

### Example

-   SeatRepository
    -   CRUD operations
    -   Custom queries
    -   Locking mechanisms

Repositories should contain no business logic.

------------------------------------------------------------------------

## 5. entity/

Database model definitions.

### Purpose

-   Defines JPA entities.
-   Maps Java classes to database tables.
-   Represents seat lifecycle states.

### Key Entities

-   Seat
-   Flight
-   CheckIn
-   SeatStatus (Enum)
-   CheckInStatus (Enum)

Entities define data structure only.

------------------------------------------------------------------------

## 6. scheduler/

Background worker components.

### Purpose

-   Runs scheduled tasks.
-   Automatically releases expired seat holds.
-   Ensures system consistency.

### Example

-   SeatExpiryScheduler
    -   Runs every 10 seconds.
    -   Frees seats held beyond 120 seconds.

Enabled via `@EnableScheduling`.

------------------------------------------------------------------------

## 7. config/

Configuration classes.

### Purpose

-   Enables scheduling.
-   Defines application-wide configuration.
-   Can be extended for caching, security, etc.

------------------------------------------------------------------------

## 8. exception/

Centralized exception handling.

### Purpose

-   Custom business exceptions.
-   Global error handling using @ControllerAdvice.
-   Consistent API error responses.

------------------------------------------------------------------------

# src/main/resources

Contains non-Java configuration files.

    src/main/resources/

### application.yml

-   Database configuration
-   Logging levels
-   Server port
-   JPA settings

------------------------------------------------------------------------

# src/test/java/com/skyhigh/core

Contains unit and integration tests.

    src/test/java/com/skyhigh/core/

## Structure

-   controller/
-   service/

### Purpose

-   Validate business logic.
-   Ensure no seat overlap logic breaks.
-   Mock dependencies using Mockito.

Tests improve reliability and maintainability.

------------------------------------------------------------------------

# Architectural Pattern

SkyHigh Core follows:

-   Layered Architecture
-   Controller → Service → Repository pattern
-   Transactional boundaries at service layer
-   Database-backed concurrency control

This ensures:

-   Separation of concerns
-   Testability
-   Scalability
-   Maintainability

------------------------------------------------------------------------

# Design Principles Followed

-   Single Responsibility Principle
-   Clear state machine modeling (AVAILABLE → HELD → CONFIRMED)
-   Stateless service design
-   ACID transactional integrity
-   Background consistency worker

------------------------------------------------------------------------

# Summary

The project structure ensures:

✔ Clean separation of layers\
✔ Concurrency-safe seat management\
✔ Scalable backend architecture\
✔ Testable components\
✔ Maintainable codebase

This modular design allows future extensions such as Redis caching,
distributed locking, and event-driven integrations without major
refactoring.
