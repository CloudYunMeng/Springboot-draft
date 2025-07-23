# Spring Boot User Authentication API

A comprehensive Spring Boot backend API demonstrating user authentication, registration, and secure REST endpoints with H2 database persistence.

## 🚀 Features

- **User Registration & Login**: Complete authentication system
- **Password Security**: BCrypt encryption for secure password storage
- **RESTful APIs**: Full CRUD operations with authentication
- **Database Persistence**: H2 file-based database that persists data
- **Spring Security**: HTTP Basic authentication for protected endpoints
- **Backend API**: Ready for frontend integration (React, Vue, Angular, etc.)

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
   - API Base URL: http://localhost:8080
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
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123","age":30}'
```

### Login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
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
│       └── application.properties
└── test/                   # Test files
```

## 🚦 Getting Started

1. Start the application using `mvn spring-boot:run`
2. The API will be available at http://localhost:8080
3. Test the authentication endpoints using curl or Postman
4. Use the H2 console to explore the database
5. Integrate with your React frontend application

## 🔗 Frontend Integration

This backend API is designed to work with any frontend framework. For React integration:

### CORS Configuration
The API includes CORS support for frontend development. You may need to configure CORS origins in `SecurityConfig.java` based on your React app's URL.

### Authentication Flow
1. **Register**: POST to `/api/auth/register` with user details
2. **Login**: POST to `/api/auth/login` to verify credentials
3. **API Access**: Use HTTP Basic Auth with email/password for protected endpoints

### Example React Integration
```javascript
// Register user
const registerUser = async (userData) => {
  const response = await fetch('http://localhost:8080/api/auth/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(userData)
  });
  return response.json();
};

// Login user
const loginUser = async (credentials) => {
  const response = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(credentials)
  });
  return response.json();
};

// Access protected data
const getUsers = async (email, password) => {
  const credentials = btoa(`${email}:${password}`);
  const response = await fetch('http://localhost:8080/api/users', {
    headers: { 'Authorization': `Basic ${credentials}` }
  });
  return response.json();
};
```

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
