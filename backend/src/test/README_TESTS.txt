Test layout
===========

The tests are now split into explicit unit and integration folders, as required:

- src/test/java/com/yupathbuilder/backend/unit/**
- src/test/java/com/yupathbuilder/backend/integration/**

Folder organization inside each test group mirrors src/main as closely as possible.
For example:
- src/main/java/.../controller/PingController.java
- src/test/java/.../unit/controller/PingControllerUnitTest.java

How to run
==========

Unit tests only:

  Windows CMD / PowerShell
    .\mvnw.cmd test

  Git Bash / macOS / Linux
    ./mvnw test

Integration tests (real SQL database, not stub):

  Windows CMD / PowerShell
    .\mvnw.cmd verify

  Git Bash / macOS / Linux
    ./mvnw verify

Notes
=====

1. Surefire runs only *UnitTest.java files.
2. Failsafe runs only *IT.java files during verify.
3. Integration tests use the SQL store and expect the MySQL database from application.properties:
   - database: yupathbuilder
   - username: yupath
   - password: yupathpass
4. Because integration tests intentionally hit the real DB layer, MySQL must be running before verify.
5. Stub-backed database behavior is covered by unit tests in src/test/java/.../unit/store/stub.
