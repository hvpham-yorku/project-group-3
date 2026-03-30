# Refactoring Notes - Iteration 3

This document explains the main refactoring work that is visible in the final repository. The goal is not to claim more change than the repository supports, but to describe the confirmed before-and-after structure and why the refactoring mattered.

## Evidence Sources

The descriptions below are grounded in:

- the final package structure in `backend/src/main/java/com/yupathbuilder/backend/`
- earlier repository documentation that described a flatter package layout
- Git history, especially commits `5a5fbbd`, `9f4be53`, and the merge `594b983`

## 1. Backend Package Structure Refactor

### Before

Earlier project documentation and history describe a flatter backend organization with classes grouped primarily by technical role under shared root folders such as:

- `controller`
- `service`
- `repo`
- `store`
- `dto`
- `model`

### Problem

That layout made it harder to reason about the ownership of a feature end to end. A maintainer working on one domain, such as scheduling or authentication, had to jump across several unrelated root folders to follow the flow.

### Refactoring Applied

The backend was reorganized by feature. Related controllers, services, repositories, DTOs, models, and stores were moved into domain-specific packages.

### After

The final backend structure is centered on:

- `authentication`
- `course_catalog`
- `program_system`
- `scheduler_system`
- `system_status`

This makes feature boundaries clearer and reduces the cognitive load of tracing a request from controller to data access.

## 2. Authentication Refactor Into A Complete Feature Module

### Before

Git history shows an earlier authentication implementation under an older package arrangement and later references to moving from `auth` to `authentication`.

### Problem

Authentication was not only about sign-in anymore. By Iteration 3 the project also needed:

- persisted user accounts
- JWT handling
- profile reads and edits
- password changes
- program-linked user data

Keeping that work in a minimal or loosely structured auth package would have made it harder to evolve safely.

### Refactoring Applied

Authentication was reorganized into a dedicated feature module with clear internal roles:

- `AuthController`
- `AuthService`
- `UserProfileService`
- `jwt/`
- `dto/`
- `entity/`
- `repo/`
- `entity_mapper/`

### After

The final authentication module supports both session creation and ongoing account management. It is now better aligned with the rest of the backend's feature-based architecture.

## 3. Store Seams Refined Around Domain Modules

### Before

The project had to support both SQL-backed and stub-backed behavior, but earlier layouts kept stores in more central shared folders.

### Problem

SQL/stub switching is easier to test and maintain when each domain owns its own seam. Shared top-level store folders obscure which domain owns which data flow.

### Refactoring Applied

Store interfaces and implementations were placed closer to the domain that uses them:

- `program_system.store.CatalogStore`
- `course_catalog.store.CourseStore`
- `course_catalog.store.CourseDetailsStore`
- `scheduler_system.store.ScheduleStore`
- `scheduler_system.store.TermStore`

### After

The seams used by integration tests are now easier to explain and verify because the production classes and the tests align around domain boundaries.

## 4. Controller Responsibilities Became Thinner

### Before

The older backend organization made it easier for controller logic and domain logic to blur together over time.

### Problem

Controllers should primarily validate and route HTTP requests. When too much business logic accumulates there, it becomes harder to test, maintain, and reuse.

### Refactoring Applied

The feature-based refactor moved more responsibility into services and stores, leaving controllers as thinner request-entry points.

### After

The final controller layer is easier to read and aligns more closely with the testing approach:

- controller unit tests focus on request/response behavior
- integration tests focus on SQL-backed store seams

## 5. Architectural Result

The end result of the Iteration 3 refactoring is not just cleaner packaging. It also improves:

- feature ownership
- navigability for maintainers
- test seam clarity
- alignment between documentation and code structure

## Repository-State Notes

The refactoring improved structure, but a few practical repository details still matter when evaluating the final state:

- stub-mode configuration still contains unresolved merge markers that require follow-up cleanup
- the frontend still hard-codes terms instead of using the backend term endpoint

Those points do not change the value of the refactoring outcome, but they do affect how the final repository should be interpreted.
