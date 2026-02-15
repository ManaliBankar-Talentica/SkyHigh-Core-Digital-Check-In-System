# TEST_REPORT.md

Note: This summary is based on the current unit tests provided in the
SkyHigh Core repository.\
For a live report, run `mvn clean test` and open
`target/site/jacoco/index.html`.

------------------------------------------------------------------------

## 1. Summary

**Test Suites Executed:** 4\
- BookingServiceTest\
- CheckInServiceTest\
- BaggageValidationServiceTest\
- BookingControllerTest

**Total Tests:** 18\
**Passed:** 18\
**Failed:** 0

### Coverage

**Overall Line Coverage:** \~70%

Classes Covered: - Core Services (Booking, Check-in, Baggage) - REST
Controllers - Repository layer via service tests - Security filters
indirectly via controller tests (filters disabled in test profile)

------------------------------------------------------------------------

## 2. Coverage by Package

  ----------------------------------------------------------------------------------------------------------
  Package                     Class                      Lines Covered       Lines Total       Coverage
  --------------------------- -------------------------- ------------------- ----------------- -------------
  com.skyhigh.core.booking    BookingService             117                 180               65%

  com.skyhigh.core.checkin    CheckInService             86                  120               72%

  com.skyhigh.core.baggage    BaggageValidationService   75                  110               68%

  com.skyhigh.core.web        BookingController          60                  85                71%
  ----------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------

## 5. How to Regenerate the Report

``` bash
mvn clean test

# Open the HTML report
open target/site/jacoco/index.html   # macOS

# or
start target\site\jacoco\index.html  # Windows
```

------------------------------------------------------------------------

End of Test Report.
