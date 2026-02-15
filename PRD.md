# SkyHigh Core -- Digital Check-In System

## Product Requirements Document (PRD)

------------------------------------------------------------------------

## 1. Problem Statement

SkyHigh Airlines needs a scalable and reliable digital check-in backend
system capable of handling heavy peak-hour traffic at airports. During
check-in windows, hundreds of passengers attempt to:

-   View seat maps
-   Select seats
-   Add baggage
-   Complete payment (if required)
-   Finalize check-in

The current manual and semi-digital process risks: - Seat overlaps -
System slowdowns during peak load - Inconsistent seat assignments -
Payment and baggage validation issues

The goal is to build **SkyHigh Core**, a backend service that guarantees
seat consistency, fast response times, and automated validation
workflows.

------------------------------------------------------------------------

## 2. Goals & Objectives

The system must achieve the following:

### Functional Goals

1.  **No Seat Overlaps**
    -   Ensure that no two passengers can confirm the same seat.
    -   Guarantee conflict-free seat reservation under concurrency.
2.  **Time-Bound Seat Holding**
    -   When a seat is selected, it must be held for exactly 120
        seconds.
    -   If not confirmed, it must automatically revert to AVAILABLE.
3.  **Fast Seat Map Access**
    -   Seat map browsing must load quickly even during peak traffic.
4.  **Baggage Validation**
    -   Maximum allowed baggage weight: 25kg.
    -   If exceeded, check-in pauses until payment is completed.
5.  **Clear Check-in State Tracking**
    -   IN_PROGRESS
    -   WAITING_PAYMENT
    -   COMPLETED
6.  **High Concurrency Handling**
    -   Support hundreds of concurrent users without data inconsistency.

------------------------------------------------------------------------

## 3. Key Users

### Primary Users

-   Airline passengers using self-service kiosks or mobile apps.
-   Web check-in users during peak flight windows.

### Secondary Users

-   Airport staff monitoring seat assignments.
-   Operations teams tracking check-in completion metrics.

### System Users

-   External Payment Service
-   External Weight Validation Service

------------------------------------------------------------------------

## 4. Functional Requirements (FRs)

FR-1: Seat lifecycle management (AVAILABLE → HELD → CONFIRMED)\
FR-2: 120-second automatic hold expiry\
FR-3: Concurrency-safe seat locking\
FR-4: Baggage weight validation\
FR-5: Payment pause & resume workflow\
FR-6: Real-time seat map retrieval\
FR-7: Persistent audit logging of seat transitions

------------------------------------------------------------------------

## 5. Non-Functional Requirements (NFRs)

### 5.1 Performance

-   P95 seat map response time \< 1 second.
-   Support 500+ concurrent users.
-   Seat hold operation response time \< 300ms.

### 5.2 Scalability

-   Horizontally scalable stateless service.
-   Database optimized with indexing.
-   Ready for caching layer integration (e.g., Redis).

### 5.3 Reliability

-   Zero seat duplication under concurrent load.
-   Automatic seat recovery after hold expiry.
-   ACID-compliant transactional integrity.

### 5.4 Availability

-   System uptime target: 99.9%.
-   Graceful failure handling for payment services.

### 5.5 Security

-   Input validation for seat selection and baggage weight.
-   Protection against abusive access patterns.
-   Logging and monitoring for suspicious traffic.

### 5.6 Maintainability

-   Modular service architecture.
-   Unit test coverage for controller and service layers.
-   Structured logging for observability.

### 5.7 Observability

-   Application logs for seat transitions.
-   Metrics collection for:
    -   Seat hold attempts
    -   Payment failures
    -   Check-in completion rate

------------------------------------------------------------------------

## 6. Success Metrics

-   0 seat conflicts in production.
-   99% successful check-in completion rate.
-   \<1 second seat map load at peak.
-   Automated release of expired seat holds.

------------------------------------------------------------------------

## 7. Future Enhancements

-   Redis caching for ultra-fast seat browsing.
-   Event-driven architecture using Kafka.
-   Distributed seat locking mechanism.
-   Real-time analytics dashboard.

------------------------------------------------------------------------

## 8. Summary

SkyHigh Core ensures:

✔ Conflict-free seat assignment\
✔ High-performance seat map access\
✔ Automated hold expiry\
✔ Baggage validation with payment workflow\
✔ Scalable and reliable architecture

This system forms the backbone of SkyHigh Airlines' modern digital
check-in experience.
