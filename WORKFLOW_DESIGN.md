# SkyHigh Core -- Workflow Design Document

## Overview

This document explains the implementation workflows of SkyHigh Core,
including:

-   Primary business flows
-   Seat lifecycle management
-   Baggage validation workflow
-   Background seat expiry worker
-   Database schema design
-   State storage strategy

The system is designed to ensure concurrency safety, transactional
consistency, and high performance under peak load.

------------------------------------------------------------------------

# 1. Primary Workflows

------------------------------------------------------------------------

## 1.1 Seat Selection & Hold Flow

### Description

When a passenger selects a seat:

1.  System checks seat status.
2.  If AVAILABLE → transition to HELD.
3.  Set holdExpiryTime = currentTime + 120 seconds.
4.  Save transaction atomically.

If another user tries at the same time → database locking prevents
duplication.

------------------------------------------------------------------------

### Flow Diagram

    Passenger
       |
       v
    Select Seat
       |
       v
    SeatService.holdSeat()
       |
       v
    Check Status?
       |
       +-- NOT AVAILABLE → Error
       |
       +-- AVAILABLE → Mark HELD
                         |
                         v
                 Set Expiry Time (120s)
                         |
                         v
                      Save
                         |
                         v
                   Return Success

------------------------------------------------------------------------

## 1.2 Seat Confirmation Flow

### Description

When check-in proceeds:

1.  Validate seat is HELD.
2.  Validate heldByPassengerId matches.
3.  Transition HELD → CONFIRMED.
4.  Remove expiry time.
5.  Commit transaction.

------------------------------------------------------------------------

### Flow Diagram

    Passenger
       |
       v
    Confirm Seat
       |
       v
    Validate:
      - Status == HELD
      - Same Passenger?
       |
       +-- No → Error
       |
       +-- Yes → Set CONFIRMED
                      |
                      v
                    Save
                      |
                      v
                   Success

------------------------------------------------------------------------

## 1.3 Baggage Validation & Payment Flow

### Description

During check-in:

1.  Passenger enters baggage weight.
2.  If weight ≤ 25kg → COMPLETE check-in.
3.  If weight \> 25kg → WAITING_PAYMENT.
4.  After payment success → COMPLETED.

------------------------------------------------------------------------

### Flow Diagram

    Start Check-In
         |
         v
    Enter Baggage Weight
         |
         v
    Weight <= 25kg?
         |
         +-- Yes → Status = COMPLETED
         |
         +-- No → Status = WAITING_PAYMENT
                        |
                        v
                   Process Payment
                        |
                        v
                   Payment Success?
                        |
                        +-- No → Retry
                        |
                        +-- Yes → COMPLETED

------------------------------------------------------------------------

## 1.4 Automatic Seat Expiry Flow

### Description

Background worker runs every 10 seconds:

1.  Query seats where:
    -   status = HELD
    -   holdExpiryTime \< now()
2.  Set status → AVAILABLE
3.  Clear passenger & expiry fields

------------------------------------------------------------------------

### Flow Diagram

    Scheduler (Every 10s)
            |
            v
    Find Expired HELD Seats
            |
            v
    For each Seat:
       Set AVAILABLE
       Clear Passenger
       Clear Expiry
            |
            v
          Save

------------------------------------------------------------------------

# 2. Database Schema Design

The system uses PostgreSQL with JPA.

------------------------------------------------------------------------

## 2.1 Tables

### 1. flight

  Column          Type      Description
  --------------- --------- --------------------------
  id              bigint    Primary key
  flight_number   varchar   Unique flight identifier

------------------------------------------------------------------------

### 2. seat

  Column                 Type        Description
  ---------------------- ----------- ------------------------------
  id                     bigint      Primary key
  seat_number            varchar     Seat label (1A, 2B, etc.)
  status                 varchar     AVAILABLE / HELD / CONFIRMED
  held_by_passenger_id   bigint      Passenger holding seat
  hold_expiry_time       timestamp   Expiry time for HELD
  flight_id              bigint      FK to flight
  version                bigint      Optimistic locking column

------------------------------------------------------------------------

### 3. check_in

  Column           Type      Description
  ---------------- --------- -------------------------------------------
  id               bigint    Primary key
  passenger_id     bigint    Passenger reference
  seat_id          bigint    FK to seat
  baggage_weight   double    Entered baggage weight
  status           varchar   IN_PROGRESS / WAITING_PAYMENT / COMPLETED

------------------------------------------------------------------------

# 3. Database Relationships

    Flight (1) ------ (Many) Seat
    Seat (1)   ------ (Many) CheckIn

-   One flight has many seats.
-   One seat can be linked to multiple check-ins historically.
-   Each check-in references exactly one seat.

------------------------------------------------------------------------

# 4. State Management Strategy

Seat state is stored directly in the seat table.

State transitions:

AVAILABLE → HELD → CONFIRMED

Rules enforced in Service Layer:

-   Only AVAILABLE can become HELD
-   Only HELD can become CONFIRMED
-   CONFIRMED is terminal state

Optimistic locking (version column) ensures concurrency protection.

------------------------------------------------------------------------

# 5. Concurrency Strategy

The system prevents seat overlaps using:

-   Transactional boundaries
-   Database row-level locking
-   Version column for optimistic concurrency
-   State validation before updates

Guarantee: Two passengers cannot confirm the same seat.

------------------------------------------------------------------------

# 6. Scalability Considerations

-   Stateless application design
-   Horizontal scaling possible
-   Database indexed by flight_id and status
-   Ready for Redis seat-map caching
-   Background workers lightweight

------------------------------------------------------------------------

# 7. Summary

SkyHigh Core workflow design ensures:

✔ Conflict-free seat management\
✔ Automated hold expiry\
✔ Baggage validation with payment gating\
✔ Transactional consistency\
✔ High concurrency safety\
✔ Clear database state modeling

The implementation is modular, scalable, and production-ready for
airline digital check-in systems.
