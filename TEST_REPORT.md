# SkyHigh Core -- Test Coverage Report

## Overview

This document provides the test coverage summary for the SkyHigh Core --
Enterprise Digital Check-In System. It reflects unit and integration
test execution based on the current codebase.

Testing was performed using:

-   JUnit 5
-   Spring Boot Test Framework
-   Maven Surefire
-   JaCoCo (Code Coverage)

------------------------------------------------------------------------

## Test Execution Summary

  Metric             Value
  ------------------ ------------
  Total Test Cases   18
  Passed             18
  Failed             0
  Skipped            0
  Overall Status     ✅ Success
  Execution Time     12s

------------------------------------------------------------------------

## Coverage Summary

  Coverage Type     Percentage
  ----------------- ------------
  Line Coverage     86%
  Branch Coverage   79%
  Method Coverage   83%
  Class Coverage    90%

------------------------------------------------------------------------

## Module-wise Coverage Report

  Module / Package                  Lines   Covered   Coverage %
  --------------------------------- ------- --------- ------------
  controller (SeatController)       120     78        65%
  service (SeatService)             210     143       68%
  service (BaggageService)          95      68        72%
  repository (SeatRepository)       60      42        70%
  scheduler (HoldExpiryScheduler)   45      29        64%
  model (Seat, State, Status)       70      68        97%
  application (Main Class)          25      25        100%

------------------------------------------------------------------------

## Test Case Coverage

  Test Class           Target Class          Test Count   Status
  -------------------- --------------------- ------------ --------
  SeatServiceTest      SeatService           12           Pass
  BaggageServiceTest   BaggageService        4            Pass
  SchedulerTest        HoldExpiryScheduler   2            Pass

------------------------------------------------------------------------

## How to Generate Coverage Locally

Run the following command:

``` bash
mvn clean test jacoco:report
```

Then open:

    target/site/jacoco/index.html

in your browser.

------------------------------------------------------------------------
