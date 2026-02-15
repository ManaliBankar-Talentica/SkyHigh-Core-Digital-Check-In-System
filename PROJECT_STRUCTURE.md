# Project Structure

This document explains the structure of the **skyhigh-core-enterprise** project and the purpose of each major folder and key modules.

---

## High-Level Directory Tree

```
skyhigh-core-enterprise/
  pom.xml
  src/
    main/
      java/
        com/
          skyhigh/
            core/
              SkyHighCoreApplication.java
              controller/
                SeatController.java
              service/
                BaggageService.java
                SeatService.java
              repository/
                SeatRepository.java
              model/
                CheckInStatus.java
                Seat.java
                SeatState.java
              scheduler/
                HoldExpiryScheduler.java
      resources/
        application.yml
    test/
      java/
        com/
          skyhigh/
            core/
              SeatServiceTest.java
```

---

## Directory & Module Overview

### Root Directory
The root contains configuration, build, and documentation files that define how the project is built, tested, and deployed.

Common responsibilities:
- Dependency management
- Build and packaging configuration
- Environment and tooling setup
- Top-level documentation

---

### `src/`
Main application source code.

Typical responsibilities:
- Core business logic
- Domain models
- Application services
- API or interface definitions

Sub-packages are usually organized by **domain** or **layer** (e.g., controllers, services, repositories).

---

### `config/` (if present)
Centralized configuration for different environments.

Purpose:
- Environment-specific settings (dev, test, prod)
- Feature flags
- External service configuration

---

### `tests/` or `__tests__/`
Automated test suites.

Purpose:
- Unit tests for individual components
- Integration tests for cross-module behavior
- Regression protection during changes

---

### `scripts/`
Utility and automation scripts.

Purpose:
- Local development helpers
- CI/CD support scripts
- Database migrations or maintenance jobs

---

### `docs/`
Project documentation.

Purpose:
- Architecture overviews
- Design decisions
- API references
- Operational runbooks

---

### `build/` or `dist/` (if present)
Generated artifacts.

Purpose:
- Compiled or packaged output
- Deployment-ready assets

> **Note:** These directories are usually not edited directly.

---

### Key Configuration Files

| File | Purpose |
|-----|--------|
| `README.md` | Project overview and usage instructions |
| `package.json` / `pom.xml` / `build.gradle` | Dependency and build configuration |
| `.env` / `.env.example` | Environment variable definitions |
| `.gitignore` | Files excluded from version control |
| CI config files | Continuous integration setup |

---

## Architectural Intent

This project follows a **modular enterprise structure**, designed to:
- Separate concerns clearly
- Improve maintainability and scalability
- Support team-based development
- Enable independent testing of components

---

## How to Navigate the Codebase

1. Start with `README.md` for setup instructions
2. Review `src/` for core logic
3. Check `config/` for environment behavior
4. Use `tests/` to understand expected behavior
5. Refer to `docs/` for deeper architectural context

---
- New modules should follow existing naming and layering conventions.

