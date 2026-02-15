# SkyHigh Core -- Enterprise Digital Check-In System

## Overview

SkyHigh Core is a Spring Boot--based backend service that manages
airline self-check-in with strong concurrency control, time-bound seat
reservations, baggage validation, and high scalability.

This project supports: - Conflict-free seat assignment - 2-minute seat
hold expiry - Baggage validation and payment pause - High-performance
seat map access - Background expiry processing

------------------------------------------------------------------------

## Project Structure

    skyhigh-core/
    ├── pom.xml
    ├── src/main/java/com/skyhigh/core
    │   ├── controller
    │   │   └── SeatController.java
    │   ├── service
    │   │   ├── SeatService.java
    │   │   └── BaggageService.java
    │   ├── repository
    │   │   └── SeatRepository.java
    │   ├── model
    │   │   ├── Seat.java
    │   │   ├── SeatState.java
    │   │   └── CheckInStatus.java
    │   ├── scheduler
    │   │   └── HoldExpiryScheduler.java
    │   └── SkyHighCoreApplication.java
    │
    ├── src/main/resources
    │   └── application.yml
    └── src/test/java
        └── SeatServiceTest.java

------------------------------------------------------------------------

## Technology Stack

-   Java 17+
-   Spring Boot
-   Maven
-   PostgreSQL
-   Redis (Recommended)
-   Docker (Optional)

------------------------------------------------------------------------

## Prerequisites

Make sure the following are installed:

-   Java 17 or higher
-   Maven 3.8+
-   PostgreSQL 14+
-   Redis 6+ (optional but recommended)
-   Docker (optional)

------------------------------------------------------------------------

## Database Setup

### 1. Create Database

Login to PostgreSQL and run:

``` sql
CREATE DATABASE skyhigh_core;
```

### 2. Create Tables

``` sql
CREATE TABLE seats (
    id BIGSERIAL PRIMARY KEY,
    flight_id BIGINT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    hold_expiry TIMESTAMP,
    user_id VARCHAR(100)
);

CREATE TABLE checkins (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(100),
    flight_id BIGINT,
    status VARCHAR(30),
    baggage_weight DECIMAL
);
```

------------------------------------------------------------------------

## Redis Setup (Optional but Recommended)

Redis is used for:

-   Seat hold TTL
-   Seat map caching
-   Rate limiting

Start Redis using Docker:

``` bash
docker run -d -p 6379:6379 redis
```

------------------------------------------------------------------------

## Configuration

Edit `src/main/resources/application.yml`:

``` yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/skyhigh_core
    username: postgres
    password: password

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

redis:
  host: localhost
  port: 6379
```

Update credentials as per your environment.

------------------------------------------------------------------------

## Build and Run

### 1. Build the Project

``` bash
mvn clean install
```

### 2. Run the Application

``` bash
mvn spring-boot:run
```

Application will start at:

    http://localhost:8080

------------------------------------------------------------------------

## Background Worker: Seat Hold Expiry

The `HoldExpiryScheduler` automatically releases expired seat holds.

Location:

    src/main/java/com/skyhigh/core/scheduler/HoldExpiryScheduler.java

It runs at a fixed interval and:

-   Finds expired HELD seats
-   Converts them back to AVAILABLE

No separate setup is required.

------------------------------------------------------------------------

## API Endpoints

### 1. Get Seat Map

    GET /api/flights/{flightId}/seats

### 2. Hold Seat

    POST /api/seats/hold

Request Body:

``` json
{
  "seatId": 12,
  "userId": "user123"
}
```

### 3. Confirm Seat

    POST /api/seats/confirm

### 4. Add Baggage

    POST /api/checkin/baggage

### 5. Payment Processing

    POST /api/checkin/pay

------------------------------------------------------------------------

## Check-In Status Flow

  Status            Description
  ----------------- ------------------------------------
  IN_PROGRESS       Check-in ongoing
  WAITING_PAYMENT   Payment pending for excess baggage
  COMPLETED         Check-in finished

------------------------------------------------------------------------

## Concurrency Handling

This system ensures conflict-free seat booking using:

-   Database row-level locking
-   Atomic updates
-   Transaction isolation
-   Optional Redis locking

Example:

``` sql
SELECT * FROM seats
WHERE id = ? AND status = 'AVAILABLE'
FOR UPDATE;
```

------------------------------------------------------------------------

## Performance Optimization

-   Redis cache for seat maps
-   Database indexing on flight_id
-   Connection pooling
-   Horizontal scaling support

Target: - P95 latency \< 1 second for seat browsing

------------------------------------------------------------------------

## Abuse Prevention

-   API rate limiting
-   Redis token bucket
-   IP throttling
-   Request monitoring

------------------------------------------------------------------------

## Testing

### Run Unit Tests

``` bash
mvn test
```

Test files are located in:

    src/test/java

------------------------------------------------------------------------

## Docker Deployment (Optional)

### Sample docker-compose

``` yaml
version: '3'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db
      - redis

  db:
    image: postgres:14
    environment:
      POSTGRES_DB: skyhigh_core
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password

  redis:
    image: redis
```

Start:

``` bash
docker-compose up -d
```

------------------------------------------------------------------------

## Monitoring (Optional)

-   Prometheus
-   Grafana
-   Spring Actuator
-   ELK Stack

------------------------------------------------------------------------

## Troubleshooting

  Problem                 Solution
  ----------------------- -------------------------------
  Seats not releasing     Check scheduler logs
  Database timeout        Increase connection pool size
  Redis connection fail   Verify Redis service status
  Slow API response       Enable caching and indexing

------------------------------------------------------------------------

## Future Enhancements

-   Distributed lock manager
-   Kafka-based event processing
-   Payment gateway integration
-   WebSocket live seat updates
-   Kubernetes deployment

------------------------------------------------------------------------

## License

MIT License

------------------------------------------------------------------------

## Maintained By

SkyHigh Engineering Team
