# Restful-Booker API Test Automation (REST Assured + TestNG)

This project is a **Java-based API test automation framework** for the public **Restful-Booker** API.[^1][^2]
It is designed to look and feel like a real-world QA/SDET portfolio project, with full traceability from **Test Plan → Test Scenarios → JIRA-like Test Cases → Automated Tests**.

***

## 🔍 Target API

- **Base URL:** `https://restful-booker.herokuapp.com`[^1]
- **Docs:** `https://restful-booker.herokuapp.com/apidoc/index.html`[^3]
- **API Type:** Sample hotel booking CRUD API with authentication and several built-in bugs for practice.[^2][^1]

Covered endpoints:

- `GET /ping` – Health Check
- `POST /auth` – Create Token
- `POST /booking` – Create Booking
- `GET /booking` – Get All Booking IDs
- `GET /booking/{id}` – Get Booking By ID
- `PUT /booking/{id}` – Update Booking
- `PATCH /booking/{id}` – Partial Update Booking
- `DELETE /booking/{id}` – Delete Booking[^4][^3]

***

## 🧱 Tech Stack \& Design

**Languages \& Libraries**

- Java (JDK 11+)
- REST Assured – HTTP client \& assertions[^5][^6]
- TestNG – test runner \& annotations[^7][^8]
- Jackson – JSON ↔ Java object mapping
- Lombok – POJO boilerplate reduction
- Hamcrest – matcher assertions

**Architecture**

- **Service Object Model** (like Page Object, but for APIs)
    - `AuthService` – `/auth`
    - `BookingService` – `/booking`, `/booking/{id}`
    - `HealthService` – `/ping`
- **Base Layer**
    - `BaseService` – common REST Assured setup (baseURI, content type, reusable HTTP methods)
    - `BaseTest` – loads `base.url` from `config.properties`
- **Models (POJOs)**
    - `AuthRequest`, `AuthResponse`
    - `Booking`, `BookingDates`
    - `GetBookingResponse`, etc.
- **Test Classes**
    - `HealthCheckTest` – API-001 / TS_01
    - `AuthTests` – API-002, API-003
    - `CreateBookingTest` – API-004
    - `GetBookingTest` – API-005, API-006
    - `UpdateBookingTest` – API-008, API-009
    - `DeleteBookingTest` – API-011, API-012

Traceability:

- **TS\_XX** – Test Scenario IDs (Test Plan level)
- **API-XXX** – JIRA-style Test Case IDs, mapped in `@Test(description = "...")`

***

## ✅ Implemented Test Coverage

**Functional Coverage**

- Health Check (`GET /ping`) – status `201` (API up)[^9]
- Auth:
    - Valid credentials → token returned
    - Invalid credentials → `"reason": "Bad credentials"`
- Create Booking:
    - Valid payload → `200 OK`, `bookingid` present, body matches input[^10][^9]
- Get Booking:
    - Get all booking IDs
    - Get booking by ID (using `@BeforeMethod` setup)
    - JSON schema validation for booking response
- Update Booking:
    - Authorized `PUT` with token (cookie) → fields updated
    - Unauthorized `PUT` without token → `403 Forbidden`[^4][^9]
- Delete Booking:
    - Authorized `DELETE` with token → `201 Created`
    - (Optional) follow-up `GET` to confirm `404`
    - Unauthorized `DELETE` → `403 Forbidden`

**Quality Practices**

- JSON Schema validation for response bodies[^11][^12]
- POJO deserialization for business-level assertions
- Data setup/teardown using `@BeforeMethod` / `@AfterMethod`
- Clear separation of:
    - Test data setup
    - Action under test
    - Assertions
    - Logging

***

## 📁 Project Structure

```text
restful-booker-automation/
├── src
│   └── test
│       └── java
│           ├── base
│           │   ├── BaseTest.java
│           │   └── BaseService.java
│           ├── services
│           │   ├── AuthService.java
│           │   ├── BookingService.java
│           │   └── HealthService.java
│           ├── models
│           │   ├── AuthRequest.java
│           │   ├── AuthResponse.java
│           │   ├── Booking.java
│           │   └── BookingDates.java
│           └── tests
│               ├── HealthCheckTest.java
│               ├── AuthTests.java
│               ├── CreateBookingTest.java
│               ├── GetBookingTest.java
│               ├── UpdateBookingTest.java
│               └── DeleteBookingTest.java
├── src
│   └── main
│       └── resources
│           ├── config.properties
│           └── schemas/
│               └── get-booking-schema.json
├── pom.xml
└── README.md
```


***

## ⚙️ Setup \& Execution

### Prerequisites

- JDK 11+ installed
- Maven installed
- IDE (IntelliJ / Eclipse)


### Configuration

`src/main/resources/config.properties`:

```properties
base.url=https://restful-booker.herokuapp.com
```


### Run tests (Maven)

Run all TestNG tests:

```bash
mvn test
```

Run a specific class:

```bash
mvn test -Dtest=CreateBookingTest
```

Run a specific method:

```bash
mvn test -Dtest=UpdateBookingTest#updateBookingAuthorizedTest
```

Reports:

- Default TestNG reports under `target/surefire-reports`[^13]

***

## 🧪 Example: Create Booking Test (API-004)

```java
@Test(description = "API-004 / TS_04: Create Booking - happy path")
public void createBooking_shouldReturn200AndBookingId() {

    String requestBody = """
        {
          "firstname": "Jim",
          "lastname": "Brown",
          "totalprice": 111,
          "depositpaid": true,
          "bookingdates": {
            "checkin": "2018-01-01",
            "checkout": "2019-01-01"
          },
          "additionalneeds": "Breakfast"
        }
        """;

    given()
        .baseUri(baseUrl)
        .contentType("application/json")
        .body(requestBody)
    .when()
        .post("/booking")
    .then()
        .statusCode(200)
        .body("bookingid", notNullValue())
        .body("booking.firstname", equalTo("Jim"));
}
```


***

## 🎯 Why this project is on GitHub

This repository is meant to demonstrate:

- Ability to **analyze API docs** and design a **test plan**.[^3][^4]
- Mapping between **Test Scenarios**, **JIRA-style Test Cases**, and **automated code**.
- Building a **clean, maintainable REST Assured + TestNG framework** using industry patterns (Service Object Model, POJOs, config management).
- Use of **JSON Schema validation**, **deserialization**, and **negative testing** for a public API.

If you are a recruiter or hiring manager and want a code walkthrough or an extension (e.g., reporting with Allure/Extent, CI integration), that can be added on top of this base.

***

## 🔗 References

- Restful-Booker API \& Docs[^2][^3][^1]
- REST Assured usage \& examples[^6][^5]
- Sample Restful-Booker automation projects for inspiration[^14][^15][^16][^17]
<span style="display:none">[^18][^19][^20][^21][^22][^23][^24][^25]</span>

<div align="center">⁂</div>

[^1]: https://restful-booker.herokuapp.com

[^2]: https://documenter.getpostman.com/view/4805376/RznFoxY8

[^3]: https://restful-booker.herokuapp.com/apidoc/index.html

[^4]: https://www.postman.com/automation-in-testing/restful-booker-collections/documentation/55eh7vh/restful-booker

[^5]: https://www.geeksforgeeks.org/software-testing/how-to-test-api-with-rest-assured/

[^6]: https://codoid.com/api-testing/how-to-perform-api-test-automation-using-rest-assured/

[^7]: https://www.browserstack.com/guide/what-is-testng

[^8]: https://artoftesting.com/testng-annotations

[^9]: https://docs.robotframework.org/docs/examples/restfulbooker

[^10]: https://github.com/DannyDainton/All-Things-Postman/blob/master/Examples/10_createNewBookings.md

[^11]: https://mfaisalkhatri.github.io/2022/10/30/jsonschemavalidation/

[^12]: https://dzone.com/articles/how-to-perform-json-schema-validation-in-api

[^13]: https://github.com/cvenkatreddy/restful-booker-test

[^14]: https://github.com/Tahanima/restful-booker-api-test-automation

[^15]: https://github.com/mfaisalkhatri/rest-assured-examples

[^16]: https://adamsajewicz.github.io/portfolio/2-rest-assured/

[^17]: https://github.com/AbdallahHassanTorky/Restful-Booker

[^18]: https://developers.booking.com/connectivity/docs

[^19]: https://www.youtube.com/watch?v=OcGPbOgCc60

[^20]: https://docs.pingidentity.com/pingoneaic/latest/am-rest/rest-endpoints.html

[^21]: https://mfaisalkhatri.github.io/2022/03/14/endtoendapitestingrest-assured/

[^22]: https://github.com/DominikCLK/Restful-Booker-API-Tests

[^23]: https://developers.newbook.cloud/ota.php?api_options=rest

[^24]: https://www.youtube.com/watch?v=o9KJhGHl49M

[^25]: https://gist.github.com/rppowell-lasfs/522b2698825f362a83cf4af992c18800

