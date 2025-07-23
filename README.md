# Spring Boot User Authentication Demo

A comprehensive Spring Boot application demonstrating user authentication, registration, and secure API endpoints with H2 database persistence.

## 🚀 Features

- **User Registration & Login**: Complete authentication system
- **Password Security**: BCrypt encryption for secure password storage
- **RESTful APIs**: Full CRUD operations with authentication
- **Database Persistence**: H2 file-based database that persists data
- **Spring Security**: HTTP Basic authentication for protected endpoints
- **Interactive Demo**: Web interface for testing all features

## 🛠️ Technologies Used

- **Spring Boot 3.4.1** - Main application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database access layer
- **H2 Database** - Embedded database with file persistence
- **BCrypt** - Password encryption
- **Maven** - Build tool and dependency management
- **Java 17** - Programming language

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## 🔧 Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone <your-repo-url>
   cd AMSDraft
   ```

2. **Build the project**:
   ```bash
   mvn clean compile
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**:
   - Main app: http://localhost:8080
   - Interactive demo: http://localhost:8080/index.html
   - H2 Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:file:./data/demo_db`
     - Username: `sa`
     - Password: `password`

## 📡 API Endpoints

### Public Endpoints (No Authentication)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login user |

### Protected Endpoints (Authentication Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |
| GET | `/api/users/search?name={name}` | Search users by name |
| GET | `/api/users/email/{email}` | Find user by email |

## 🧪 Testing the API

### Register a new user:
```bash
curl -X POST http://localhost:8080/api/auth/register 
  -H "Content-Type: application/json" 
  -d '{"name":"John Doe","email":"john@example.com","password":"password123","age":30}'
```

### Login:
```bash
curl -X POST http://localhost:8080/api/auth/login 
  -H "Content-Type: application/json" 
  -d '{"email":"john@example.com","password":"password123"}'
```

### Access protected endpoints:
```bash
curl -u "john@example.com:password123" http://localhost:8080/api/users
```

## 👥 Pre-loaded Test Users

The application comes with sample users (password: `password123`):
- john.doe@example.com
- jane.smith@example.com
- bob.johnson@example.com
- alice.brown@example.com

## 🗄️ Database

- **Type**: H2 Database (file-based)
- **Location**: `./data/demo_db.mv.db`
- **Persistence**: Data survives application restarts
- **Console**: Available at `/h2-console` for direct database access

## 🔐 Security Features

- **Password Encryption**: BCrypt with salt
- **Authentication**: HTTP Basic Auth
- **Protected Endpoints**: All user data requires authentication
- **Password Hiding**: Passwords excluded from API responses
- **Session Management**: Secure session handling

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── dto/            # Data transfer objects
│   │   ├── model/          # JPA entities
│   │   ├── repository/     # Data repositories
│   │   ├── service/        # Business logic
│   │   └── DemoApplication.java
│   └── resources/
│       ├── static/         # Static web content
│       └── application.properties
└── test/                   # Test files
```

## 🚦 Getting Started

1. Start the application using `mvn spring-boot:run`
2. Visit http://localhost:8080/index.html for the interactive demo
3. Try registering a new user and logging in
4. Test the protected endpoints with authentication
5. Explore the H2 database console to see stored data

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is for demonstration purposes.

## 🔧 Configuration

Key configuration in `application.properties`:
- Server port: 8080
- Database: H2 file-based
- JPA: Auto-update schema
- H2 Console: Enabled for development

## 🐛 Troubleshooting

- **Database locked**: Stop the application and restart
- **Port 8080 in use**: Change `server.port` in application.properties
- **Authentication fails**: Check credentials and ensure user exists

---

**Happy coding!** 🎉

## Features

- Spring Boot 3.4.1
- Java 17
- Maven build system
- Spring Web (REST APIs)
- Spring Boot DevTools (for development)
- JUnit 5 testing

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/demo/
│   │       ├── DemoApplication.java          # Main application class
│   │       └── controller/
│   │           └── HelloController.java      # REST controller
│   └── resources/
│       └── application.properties            # Application configuration
└── test/
    └── java/
        └── com/example/demo/
            └── DemoApplicationTests.java     # Basic test class
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Build the project:
   ```bash
   mvn clean compile
   ```

2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

3. The application will start on `http://localhost:8080`

### Available Endpoints

- `GET /` - Returns "Hello, Spring Boot!"
- `GET /api/hello` - Returns "Hello from API!"

### Running Tests

```bash
mvn test
```

## Development

This project includes Spring Boot DevTools for enhanced development experience with automatic restarts when code changes are detected.
