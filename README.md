# Restful-Booker API Test Automation Framework

This project is a comprehensive **API test automation framework** for the **Restful-Booker** API, built using **Java**, **REST Assured**, and **TestNG**. It demonstrates a scalable **Service Object Model** design, separating business logic, data models, and test execution for maximum maintainability and clarity.

The framework covers critical end-to-end workflows including authentication, booking management (CRUD), and health checks, with built-in schema validation and logging.

---

## 📁 Project Structure

The project follows a clean, modular package structure under `src/test/java/com.api`:

```text
src
└── test
    └── java
        └── com.api
            ├── base                    # Core framework & Service logic
            │   ├── BaseService.java       # Base REST Assured config & HTTP wrappers
            │   ├── BookingService.java    # CRUD operations for /booking endpoints
            │   ├── GenerateTokenService.java # Auth operations (/auth)
            │   └── GetBookingIDService.java  # Booking lookup logic
            │
            ├── listeners               # TestNG Execution Hooks
            │   └── TestListener.java      # Reporting/Logging integration
            │
            ├── models                  # POJO Data Models (Jackson)
            │   ├── request             # Request Payloads
            │   │   ├── BookingDates.java
            │   │   ├── BookingRequest.java
            │   │   └── LoginRequest.java
            │   │
            │   └── response            # Response DTOs
            │       ├── BookingResponse.java
            │       ├── CreateBookingResponse.java
            │       ├── GetBookingResponse.java
            │       └── LoginResponse.java
            │
            └── test                    # TestNG Test Classes
                ├── HealthCheckTest.java       # API-001: Health Check
                ├── GenerateTokenTest.java     # API-002: Auth Token
                ├── CreateBookingResponseTest.java # API-004: Create Booking
                ├── GetBookingResponseTest.java    # API-005: Get Booking Details
                ├── GetBookingResponseIdTest.java  # API-006: Get Booking IDs
                ├── UpdateBookingTest.java     # API-008: Update Booking
                ├── PatchBookingTest.java      # API-010: Partial Update
                └── DeleteBookingTest.java     # API-011: Delete Booking

resources
├── schemas                     # JSON Schemas for validation
│   ├── create-booking-schema.json
│   └── get-booking-schema.json
└── log4j2.xml                  # Logging configuration
```

---

## 🔧 Tech Stack

- **Language:** Java (JDK 11+)
- **Testing Framework:** TestNG
- **API Client:** REST Assured
- **Object Mapper:** Jackson (POJO Serialization/Deserialization)
- **Logging:** Log4j2
- **Validation:** JSON Schema Validator + Hamcrest Matchers
- **Build Tool:** Maven

---

## 🧱 Key Design Patterns

### 1. Service Object Model (Base Layer)
Instead of writing raw REST Assured calls in tests, we use **Service Classes** (e.g., `BookingService`, `GenerateTokenService`).
- **`BaseService`**: Manages the `RequestSpecification`, base URL, and common HTTP methods (GET, POST, PUT, DELETE).
- **Domain Services**: Encapsulate specific API operations. For example, `BookingService.createBooking()` handles the endpoint path, headers, and serialization, returning a `Response` object.

### 2. POJO Data Models (Models Layer)
Requests and responses are mapped to Java Objects using **Jackson**.
- **`BookingRequest`**: Builder pattern for creating valid booking payloads.
- **`BookingResponse`**: Strongly typed response objects for type-safe assertions (e.g., `response.getBookingid()`).

### 3. Separation of Concerns (Test Layer)
Test classes focus **only** on business logic and assertions.
- Data setup is handled in `@BeforeMethod`.
- API calls are delegated to Services.
- Assertions verify status codes, schema compliance, and logical data integrity.

---

## ✅ Test Coverage & Features

| Endpoint | Test Class | Coverage Description |
| :--- | :--- | :--- |
| **GET /ping** | `HealthCheckTest` | Verifies API is up (Status 201). |
| **POST /auth** | `GenerateTokenTest` | Validates token generation with valid/invalid credentials. |
| **POST /booking** | `CreateBookingResponseTest` | Creates booking, validates schema, verifies data persistence. |
| **GET /booking/{id}** | `GetBookingResponseTest` | Fetches booking details, validates POJO fields. |
| **GET /booking** | `GetBookingResponseIdTest` | Retrieves list of booking IDs. |
| **PUT /booking/{id}** | `UpdateBookingTest` | Updates existing booking (Auth required). |
| **PATCH /booking/{id}** | `PatchBookingTest` | Partially updates booking details. |
| **DELETE /booking/{id}** | `DeleteBookingTest` | Deletes booking and verifies 404 on subsequent GET. |

---

## ⚙️ How to Run

### Prerequisites
- Java JDK 11 or higher
- Maven installed

### Run All Tests
Execute the full suite via Maven:
```bash
mvn clean test
```

### Run Specific Test Class
To run only the Create Booking tests:
```bash
mvn test -Dtest=CreateBookingResponseTest
```

### Reporting
After execution, TestNG reports are generated in:
- `target/surefire-reports/index.html`
- `target/surefire-reports/emailable-report.html`

---

## 📝 Configuration

- **Base URL:** Configured in `BaseService` (Default: `https://restful-booker.herokuapp.com`).
- **Logging:** Configured via `src/test/resources/log4j2.xml`. Logs are printed to console and/or file depending on configuration.
- **Schemas:** JSON schema files located in `src/test/resources/schemas` are used for contract testing.

---

## 🔗 References
- **API Documentation:** [Restful-Booker API Docs](https://restful-booker.herokuapp.com/apidoc/index.html)
- **REST Assured:** [REST Assured Usage Guide](https://github.com/rest-assured/rest-assured/wiki/Usage)
- **TestNG:** [TestNG Documentation](https://testng.org/doc/documentation-main.html)
