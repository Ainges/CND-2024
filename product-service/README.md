
# **Product Service**

The **Product Service** is a backend application built using Express.js. It provides APIs for managing products, such as creating, retrieving, updating, and deleting them. Additionally, it includes Swagger documentation for API exploration.

---

## **Prerequisites**

### **1. Install Node.js**
- Ensure Node.js (version 14 or higher) is installed on your system. You can download it [here](https://nodejs.org/).

---

### **Step 1: Install Dependencies**
Install the required npm packages:
```bash
npm install
```

### **Step 2: Start the Application**
Start the application locally:
```bash
npm start
```

The service will be available at `http://localhost:4000`.

---

## **API Documentation**

### **Swagger UI**
The API documentation is available at:
```
http://localhost:4000/apidocs
```

---

## **REST API Endpoints**

### **1. Get All Products**
- **Endpoint:** `GET /products`
- **Description:** Retrieve a list of all products.
- **Response:**
  ```json
  [
    {
      "id": 1,
      "name": "Product 1",
      "description": "Description of product 1",
      "price": 100.0
    },
    {
      "id": 2,
      "name": "Product 2",
      "description": "Description of product 2",
      "price": 150.0
    }
  ]
  ```

### **2. Create a New Product**
- **Endpoint:** `POST /products`
- **Description:** Add a new product to the inventory.
- **Request Body:**
  ```json
  {
    "name": "New Product",
    "description": "Description of the new product",
    "price": 200.0
  }
  ```
- **Response:**
  ```json
  {
    "id": 3,
    "name": "New Product",
    "description": "Description of the new product",
    "price": 200.0
  }
  ```

### **3. Get a Product by ID**
- **Endpoint:** `GET /products/:id`
- **Description:** Retrieve details of a specific product.
- **Response:**
  ```json
  {
    "id": 1,
    "name": "Product 1",
    "description": "Description of product 1",
    "price": 100.0
  }
  ```

### **4. Update a Product**
- **Endpoint:** `PUT /products/:id`
- **Description:** Update the details of an existing product.
- **Request Body (partial or full updates):**
  ```json
  {
    "name": "Updated Product",
    "description": "Updated description",
    "price": 250.0
  }
  ```
- **Response:**
  ```json
  {
    "id": 1,
    "name": "Updated Product",
    "description": "Updated description",
    "price": 250.0
  }
  ```

### **5. Delete a Product**
- **Endpoint:** `DELETE /products/:id`
- **Description:** Remove a product from the inventory.
- **Response:**
  ```json
  {
    "message": "Product deleted successfully."
  }
  ```

---

## **Running Tests**

Run unit tests using the following command:
```bash
npm test
```

---

## **Running in Docker**

### **Step 1: Build the Docker Image**
```bash
docker build -t product-service .
```

### **Step 2: Run the Docker Container**
```bash
docker run -p 4000:4000 product-service
```

### **Step 3: Access Swagger UI**
Visit `http://localhost:4000/apidocs` to explore the API.

