
# **User Service**

The **User Service** is a backend application built using Flask that provides functionalities for managing users. It adheres to the **Hexagonal Architecture** principles, ensuring flexibility and scalability. This service exposes RESTful APIs and CLI commands for user management.

---

## **Prerequisites**

### **1. Install Python**
- Ensure Python 3.10 or higher is installed on your machine. You can download it [here](https://www.python.org/downloads/).

### **2. Install Virtual Environment**
- It is recommended to use a virtual environment to manage dependencies locally.

---

## **Setup Instructions**

### **Step 1: Create a Virtual Environment**
Create a virtual environment in the project directory:
```bash
python -m venv venv
```

Activate the virtual environment:
- On Linux/MacOS:
  ```bash
  source venv/bin/activate
  ```
- On Windows:
  ```bash
  venv\Scripts\activate
  ```

### **Step 2: Install Dependencies**
Install all required packages using `pip`:
```bash
pip install -r requirements.txt
```

### **Step 3: Start the Application**
Run the Flask application:
```bash
flask run
```

The service will be available at `http://localhost:5000`.

---

## **REST API Endpoints**

### **1. Register a User**
- **Endpoint:** `POST /register`
- **Description:** Register a new user.
- **Request Body:**
  ```json
  {
    "email": "user@example.com",
    "password": "securepassword",
    "role": "User",
    "first_name": "John",
    "last_name": "Doe"
  }
  ```
- **Response:**
  ```json
  {
    "message": "User registered successfully",
    "user_id": 1
  }
  ```

### **2. Login a User**
- **Endpoint:** `POST /login`
- **Description:** Login an existing user.
- **Request Body:**
  ```json
  {
    "email": "user@example.com",
    "password": "securepassword"
  }
  ```
- **Response:**
  ```json
  {
    "message": "Login successful",
    "user_id": 1
  }
  ```

### **3. Get a User by ID**
- **Endpoint:** `GET /users/<user_id>`
- **Description:** Retrieve details of a user.
- **Response:**
  ```json
  {
    "id": 1,
    "email": "user@example.com",
    "first_name": "John",
    "last_name": "Doe",
    "role": "User"
  }
  ```

### **4. Update a User**
- **Endpoint:** `PUT /users/<user_id>`
- **Description:** Update user details.
- **Request Body (partial or full updates):**
  ```json
  {
    "new_email": "updated@example.com",
    "new_first_name": "Johnathan",
    "new_last_name": "Smith"
  }
  ```
- **Response:**
  ```json
  {
    "message": "User updated successfully",
    "user": {
      "id": 1,
      "email": "updated@example.com",
      "first_name": "Johnathan",
      "last_name": "Smith"
    }
  }
  ```

### **5. Delete a User**
- **Endpoint:** `DELETE /users/<user_id>`
- **Description:** Delete a user.
- **Response:**
  ```json
  {
    "message": "User deleted successfully"
  }
  ```

---

## **CLI Commands**

The service also supports management via **CLI commands**. To use these commands, ensure the service is running in the appropriate environment.

### **Available Commands**

1. **Register a User**
   ```bash
   flask register-user <email> <password> <role> [--first_name <first_name>] [--last_name <last_name>]
   ```
   **Example:**
   ```bash
   flask register-user admin@example.com securepassword Admin --first_name John --last_name Doe
   ```

2. **Get a User by ID**
   ```bash
   flask get-user <user_id>
   ```
   **Example:**
   ```bash
   flask get-user 1
   ```

3. **Delete a User**
   ```bash
   flask delete-user <user_id>
   ```
   **Example:**
   ```bash
   flask delete-user 1
   ```

---

## **Running in Docker**

You can also run this service in a Docker container:

1. **Build the Docker Image:**
   ```bash
   docker build -t user-service .
   ```

2. **Run the Docker Container:**
   ```bash
   docker run -p 5000:5000 user-service
   ```

3. **Execute CLI Commands in the Container:**
   ```bash
   docker exec -it <container-name> flask register-user admin@example.com securepassword Admin --first_name John --last_name Doe
   ```

---

## **Logs**

Logs are stored in `application.log`. You can view them with:
```bash
cat application.log
```

---

## **Testing the Service**

Run unit tests using `pytest`:
```bash
pytest
```

