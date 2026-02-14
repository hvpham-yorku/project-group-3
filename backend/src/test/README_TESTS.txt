How to run tests (Windows CMD):

cd backend
.\mvnw.cmd test

Notes:
- These tests are designed to be simple and stable for ITR1.
- Unit tests use Mockito and do NOT require a running server.
- AuthControllerTest uses @WebMvcTest and mocks UserService/JwtUtil/PasswordEncoder (no real file IO).
