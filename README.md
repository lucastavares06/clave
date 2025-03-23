# Clave

**Clave** is a minimal, end-to-end authentication system built with:
- **Java 21**
- **Spring Boot**
- **PostgreSQL** (via Supabase in this example)
- **JWT**-based token authentication

It currently provides two main endpoints:
1. **Sign Up** – Create a new account (`POST api/v1/auth/signup`)
2. **Log In** – Authenticate (`POST api/v1/auth/login`)

Future additions will include:
- Email validation using **Java Mail Sender**
- Password reset functionality

---

## Features
- **Spring Security** for handling authentication/authorization
- **JWT** for stateless session management
- **Spring Data JPA** for database interactions
- **application.yml** example for easy environment configuration

---

## Quick Start

1. **Clone the repository**:
   ```bash
   git clone https://github.com/lucastavares06/clave.git
   ```

2. **Navigate to the project directory**:
   ```bash
   cd clave
   ```

3. **Configure environment**:
    - Copy `application.yml.example` to `application.yml` (or set up your own configuration file).
    - Update database connection details, JWT secret, and other configurations as needed.

4. **Build and run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   The application starts on **port 8080** by default.

---

## Endpoints

### Sign Up
Create a new account:
```http
POST api/v1/auth/signup
```
**Request body**:
```json
{
  "name": "Lucas Tavares",
  "email": "lucas@email.com",
  "password": "secret"
}
```
**Response**:
- HTTP `201 CREATED` on success, returning a JSON body with user info or a confirmation.

### Log In
Authenticate with existing credentials:
```http
POST api/v1/auth/login
```
**Request body**:
```json
{
  "email": "lucas@email.com",
  "password": "secret"
}
```
**Response**:
- HTTP `200 OK` on success, returning a JSON body containing the JWT token.

---

## Sample `application.yml`

```yaml
spring:
  application:
    name: clave

  datasource:
    url: jdbc:postgresql://<host>:<port>/<database>?sslmode=require
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  jpa:
    show-sql: true
    open-in-view: false
    hibernate:
      ddl-auto: create
    properties:
      hibernate.format_sql: true

jwt:
  secret: ${JWT_SECRET}     # JWT secret key
  expiration: 86400000      # 24 hours in milliseconds
```

> **Note:** Replace `<host>`, `<port>`, and `<database>` with your actual PostgreSQL settings. For production, consider changing `ddl-auto` to `validate` or `update` (or another preferred strategy).

---

## Entity Overview

Below is the `User` entity, showcasing the fields used during signup (and in future functionalities like password resets):

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
```

---

## Future Enhancements
- **Email Validation:** Send a confirmation link using Java Mail Sender.
- **Password Reset:** Securely handle forgotten passwords.
- Additional security layers and refined logging.

---

## Contributing
Feel free to open issues or submit pull requests for improvements, bug fixes, or new features.

---

## License

This project is licensed under the [MIT License](LICENSE).


---

Thank you for using this project. If you have questions or suggestions, feel free to open an issue.
