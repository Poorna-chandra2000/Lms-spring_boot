# LMS-Springboot
# LMS API Documentation

This document provides an overview of the API endpoints for the LMS (Learning Management System). It covers user management, course enrollment, course creation, category management, and authentication.

---

## **UserController**

### 1. **Get All Users**
- **URL**: `/users`
- **Method**: `GET`
- **Description**: Retrieves a list of all registered users.
- **Response**: `200 OK` with a list of `UserDto` objects.

---

## **EnrolledController**

### 1. **Get Enrolled Courses**
- **URL**: `/api/enrolled`
- **Method**: `GET`
- **Description**: Retrieves the current user's enrolled courses.
- **Response**: `200 OK` with `EnrolledDto` object.

### 2. **Enroll in a Course**
- **URL**: `/api/enrolled/enroll/{courseId}`
- **Method**: `POST`
- **Security**: Requires `ROLE_USER`, `ROLE_ADMIN`, or `ROLE_CREATOR`.
- **Description**: Enrolls the current user in the specified course.
- **Path Variables**: 
  - `courseId`: ID of the course to enroll in.
- **Response**: `200 OK` with `EnrolledCourseDto` object.

### 3. **Get Course Content**
- **URL**: `/api/enrolled/course-content/{courseId}`
- **Method**: `GET`
- **Security**: Requires `ROLE_USER`, `ROLE_ADMIN`, or `ROLE_CREATOR`.
- **Description**: Retrieves the content of a specific course for the user if enrolled.
- **Path Variables**: 
  - `courseId`: ID of the course.
- **Response**: `200 OK` with a list of `CourseContentDto` objects.

### 4. **Get All Enrolled Courses**
- **URL**: `/api/enrolled/courses`
- **Method**: `GET`
- **Security**: Requires `ROLE_ADMIN`.
- **Description**: Retrieves a list of all courses the current user is enrolled in.
- **Response**: `200 OK` with a list of `EnrolledCourseDto` objects.

---

## **CourseController**

### 1. **Create New Course**
- **URL**: `/api/course/CreateCourse/{categoryId}`
- **Method**: `POST`
- **Description**: Creates a new course under a specified category.
- **Path Variables**: 
  - `categoryId`: ID of the category.
- **Request Body**: `CourseDto` object containing course details.
- **Response**: `200 OK` with the created `CourseDto`.

### 2. **Get Courses by Category**
- **URL**: `/api/course/getCourseByCategoryId/{categoryId}`
- **Method**: `GET`
- **Description**: Retrieves all courses belonging to a specified category.
- **Path Variables**: 
  - `categoryId`: ID of the category.
- **Response**: `200 OK` with a list of `CourseDto` objects.

### 3. **Get All Courses**
- **URL**: `/api/course/allCourse`
- **Method**: `GET`
- **Description**: Retrieves a list of all courses available on the platform.
- **Response**: `200 OK` with a list of `CourseDto` objects.

### 4. **Delete Course by ID**
- **URL**: `/api/course/delCourseById/{courseId}`
- **Method**: `DELETE`
- **Description**: Deletes a specific course by its ID.
- **Path Variables**: 
  - `courseId`: ID of the course to delete.
- **Response**: `200 OK` with a boolean indicating success.

### 5. **Get Author's Courses**
- **URL**: `/api/course/allCourse/author`
- **Method**: `GET`
- **Description**: Retrieves all courses created by the current author.
- **Response**: `200 OK` with a list of `CourseDto` objects.

### 6. **Delete Author's Course**
- **URL**: `/api/course/delCourseByAuthor/{courseId}`
- **Method**: `DELETE`
- **Security**: Checks if the current user is the owner of the course (`@postSecurity.isOwnerOfPost`).
- **Description**: Deletes a course created by the current author.
- **Path Variables**: 
  - `courseId`: ID of the course to delete.
- **Response**: `200 OK` with a boolean indicating success.

---

## **CourseContentController**

### 1. **Create Course Content**
- **URL**: `/api/createCourseContent/{courseId}`
- **Method**: `POST`
- **Description**: Creates new content for a specific course.
- **Path Variables**: 
  - `courseId`: ID of the course.
- **Request Body**: `CourseContentDto` object containing course content details.
- **Response**: `200 OK` with the created `CourseContentDto`.

### 2. **Get Course Content**
- **URL**: `/api/courseContent/{courseId}`
- **Method**: `GET`
- **Security**: Requires `ROLE_USER`, `ROLE_ADMIN`, or `ROLE_CREATOR`.
- **Description**: Retrieves the content of a specified course.
- **Path Variables**: 
  - `courseId`: ID of the course.
- **Response**: `200 OK` with a list of `CourseContentDto` objects.

---

## **CategoryController**

### 1. **Create New Category**
- **URL**: `/api/category/CreateCategory`
- **Method**: `POST`
- **Description**: Creates a new course category.
- **Request Body**: `CategoryDto` object containing category details.
- **Response**: `200 OK` with the created `CategoryDto`.

### 2. **Get All Categories**
- **URL**: `/api/category/allCategory`
- **Method**: `GET`
- **Description**: Retrieves a list of all available categories.
- **Response**: `200 OK` with a list of `CategoryDto` objects.

---

## **AuthController**

### 1. **Sign Up**
- **URL**: `/auth/signup`
- **Method**: `POST`
- **Description**: Registers a new user.
- **Request Body**: `SignUpDto` containing user details.
- **Response**: `200 OK` with the created `UserDto`.

### 2. **Login**
- **URL**: `/auth/login`
- **Method**: `POST`
- **Description**: Logs in an existing user and sets a `refreshToken` in a cookie.
- **Request Body**: `LoginDto` containing login details.
- **Response**: `200 OK` with `LoginResponseDto`, including both access and refresh tokens.

### 3. **Refresh Token**
- **URL**: `/auth/refresh`
- **Method**: `POST`
- **Description**: Refreshes the access token using the refresh token stored in cookies.
- **Response**: `200 OK` with a new `LoginResponseDto`.

### 4. **Logout**
- **URL**: `/auth/logout`
- **Method**: `DELETE`
- **Description**: Logs out the current user by invalidating their refresh token.
- **Response**: `200 OK` with a boolean indicating success.

---

### Notes
- Make sure to configure the necessary security settings in `websecurityconfig` for authentication and role-based access.
