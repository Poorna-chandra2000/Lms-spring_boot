# LMS-Springboot

Here is a detailed `README.md` for your Spring Boot LMS application:

---

# LMS Application

This project is a comprehensive Learning Management System (LMS) built using Spring Boot. It includes features for user authentication, course creation, enrollment, role-based access control, and more. The system is designed to manage online courses, allowing users to sign up, log in, view and purchase courses, and access course content based on their enrollment status.

## Features

- **User Authentication**: Secure login and signup functionality using JWT and OAuth2.
- **Role-Based Access Control**: Different roles such as `USER`, `ADMIN`, and `CREATOR` have different permissions.
- **Course Management**: Create, update, delete, and view courses, along with categorization.
- **Course Enrollment**: Users can enroll in courses and access content based on enrollment.
- **Secure Session Management**: Secure handling of cookies for refresh tokens.
- **OAuth2 Integration**: Supports OAuth2 login for easy access.

## Technology Stack

- **Backend**: Spring Boot, Spring Security, JWT, OAuth2
- **Database**: PostgreSQL (via JPA/Hibernate)
- **Build Tool**: Maven
- **Authentication**: JWT Tokens and Cookies for session management

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd LMS-Application
   ```
3. Update the `application.properties` file with your PostgreSQL database credentials and other necessary configuration.
4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Configuration

In the `application.properties`, make sure to set the environment for cookie security:
```properties
deploy.env=development
# or
deploy.env=production
```

## Endpoints

### Authentication

- **POST** `/auth/signup` - Register a new user
- **POST** `/auth/login` - Login to the system
- **POST** `/auth/refresh` - Refresh JWT using refresh token
- **DELETE** `/auth/logout` - Logout from the system

### Category Management

- **POST** `/api/category/CreateCategory` - Create a new category (Admin & Creator)
- **GET** `/api/category/allCategory` - Get all categories

### Course Management

- **POST** `/api/course/CreateCourse/{categoryId}` - Create a new course under a category (Admin & Creator)
- **GET** `/api/course/getCourseByCategoryId/{categoryId}` - Get courses by category ID
- **GET** `/api/course/allCourse` - Get all courses
- **DELETE** `/api/course/delCourseById/{courseId}` - Delete a course by ID (Admin)
- **GET** `/api/course/allCourse/author` - Get courses created by the logged-in author
- **DELETE** `/api/course/delCourseByAuthor/{courseId}` - Delete a course by author (Author can delete their own courses)

### Course Content Management

- **POST** `/api/createCourseContent/{courseId}` - Create course content for a specific course
- **GET** `/api/courseContent/{courseId}` - Get content for a specific course (Accessible by enrolled users)

### Enrollment

- **GET** `/api/enrolled` - Get enrolled courses for the current user
- **POST** `/api/enrolled/enroll/{courseId}` - Enroll in a specific course (Authenticated users)
- **GET** `/api/enrolled/course-content/{courseId}` - Get content for enrolled courses
- **GET** `/api/enrolled/courses` - Get a list of all enrolled courses (Admin)
- **GET** `/api/enrolled/users-enrolled/{courseId}` - Get users enrolled in a specific course (Admin & Creator)
- **GET** `/api/enrolled/status` - Get status (enrolled, completed) of courses for the current user

### User Management

- **GET** `/users` - Get all users (Admin only)

### Security

Security is managed using Spring Security, JWT, and OAuth2. The application uses role-based access control, ensuring that only authorized users can access certain features.

**Public Routes**:
- `/auth/**`, `/api/allCategory`, `/error`, `/home.html`, `/session/**`

**Secured Routes**:
- Accessible based on roles (`USER`, `ADMIN`, `CREATOR`) and permissions

## Security Configuration

- **JWT Authentication**: The `JwtAuthFilter` handles JWT token validation.
- **OAuth2 Login**: Supported for seamless login with external providers.
- **Session Management**: Configured for stateless session using JWT, with cookies handling refresh tokens securely.

---

This `README.md` provides a comprehensive overview of your LMS application, making it easy for new users to understand and get started with the project. Let me know if there are any additional details you'd like to include!