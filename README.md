# Clave

**Clave** is a minimal, end-to-end authentication system built with:
- **Java 21**
- **Spring Boot**
- **PostgreSQL** (via Supabase in this example)
- **JWT**-based token authentication

It currently provides the following endpoints:
1. **Sign Up** – Initiate user registration (`POST /api/v1/auth/signup`)
2. **Confirm Account** – Activate the account via email token (`GET /api/v1/auth/confirm?token=...`)
3. **Log In** – Authenticate (`POST /api/v1/auth/login`)
4. **Refresh Token** – Renew access without re-authenticating (`POST /api/v1/auth/refresh`)

Future additions will include:
- Password reset functionality

---

## Features
- **Spring Security** for handling authentication/authorization
- **JWT** for stateless session management
- **Refresh Token** support for persistent sessions
- **Spring Data JPA** for database interactions
- **Java Mail Sender** for email confirmation
- `application.yml.example` for easy environment configuration

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
   - Copy `application.yml.example` to `application.yml`.
   - Update database connection details, JWT secret, and mail credentials.

4. **Build and run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   The application starts on **port 8080** by default.

---

## Endpoints

### 📋 Sign Up (Step 1)

```http
POST /api/v1/auth/signup
```

**Request**:
```json
{
  "name": "Lucas Tavares",
  "email": "lucas@email.com",
  "password": "secret",
  "role": "USER"
}
```

**Response**:
- HTTP `201 Created` with a message confirming that an email was sent.

---

### ✅ Confirm Account (Step 2)

```http
GET /api/v1/auth/confirm?token=abc123
```

**Response**:
- HTTP `200 OK` with user info if valid.
- HTTP `410 Gone` if token is expired or invalid.

---

### 🔐 Log In

```http
POST /api/v1/auth/login
```

**Request**:
```json
{
  "email": "lucas@email.com",
  "password": "secret"
}
```

**Response**:
```json
{
  "accessToken": "jwt-access-token",
  "refreshToken": "jwt-refresh-token"
}
```

---

### 🔄 Refresh Token

```http
POST /api/v1/auth/refresh
```

**Request**:
```json
{
  "refreshToken": "your-valid-refresh-token"
}
```

**Response**:
```json
{
  "accessToken": "new-access-token",
  "refreshToken": "new-refresh-token"
}
```

**Notes**:
- The `refreshToken` has a longer expiration (default: 7 days).
- A new token pair is returned on each valid request.
- Invalid or expired tokens return `401 Unauthorized`.

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

  mail:
    host: smtp.gmail.com
    port: 587
    username: ${EMAIL_USERNAME}
    password: ${EMAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

email:
  from: ${EMAIL_USERNAME}
  sender-name: Clave
  base-confirm-url: http://localhost:8080/api/v1/auth/confirm?token=

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000           # 1 day
  refresh-expiration: 604800000  # 7 days
```

> **Note**: Replace placeholders with real values. In production, use `ddl-auto: validate` or `update` instead of `create`.

---

## Entity Overview

Below is the `User` entity, showcasing the fields used during signup:

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
- **Password Reset**: Securely handle forgotten passwords
- **OAuth**: Optional social login
- **Account Locking**: After multiple failed login attempts
- Better email templates and branding

---

## Contributing

Feel free to open issues or submit pull requests for improvements, bug fixes, or new features.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

Thanks for checking out Clave! If you have questions or suggestions, feel free to open an issue. 🚀

