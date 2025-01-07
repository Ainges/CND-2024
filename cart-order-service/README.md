## Running the application locally

# Prerequisites

Before running the application locally, make sure you have the following installed:

## 1. JDK 21 (Required only for local setup without Docker)
If you want to run the application **without Docker**, you must install the **Java Development Kit (JDK) Version 21**.

Make sure the `JAVA_HOME` environment variable is set to the directory where JDK is installed.

## 2. Docker (Required only if you want to use Docker)
If you want to use **Docker** to run RabbitMQ and the database locally, you will need **Docker**. You can download it from the official website:
- [Docker](https://www.docker.com/)

### Development Mode

To run the application in development mode, which enables live coding, use the following command:

```shell script
./mvnw compile quarkus:dev
```

In development mode, Quarkus provides a Dev UI that is accessible at <http://localhost:8080/q/dev/> and there is also a swagger-ui available at <http://localhost:8080/q/dev-ui/io.quarkus.quarkus-smallrye-openapi/swagger-ui>.

### Production Mode

To run the application in production mode, you need to package it first. Use the following command to package the application:

```shell script
./mvnw package
```

This will create a `quarkus-run.jar` file in the `target/quarkus-app/` directory. You can then run the application using:

```shell script
java -jar target/quarkus-app/quarkus-run.jar
```
In production mode the swagger-ui is available at <http://localhost:8080/q/apidocs>.

## Required Environment Variables

This application requires the following environment variables to be configured:

- `RABBITMQ_HOST`: The hostname for the RabbitMQ server (e.g., "rabbitmq").
- `RABBITMQ_PORT`: The port number for the RabbitMQ server (e.g., "5672").
- `RABBITMQ_USERNAME`: The username for RabbitMQ authentication (e.g., "guest").
- `RABBITMQ_PASSWORD`: The password for RabbitMQ authentication (e.g., "guest").
- `RABBITMQ_HTTP_PORT`: The HTTP port for RabbitMQ management (e.g., "15672").
- `RABBITMQ_QUEUE_NAME_PENDING_ORDERS`: The name of the queue for pending orders (e.g., "pending-orders").
- `RABBITMQ_EXCHANGE_NAME`: The name of the RabbitMQ exchange (e.g., "").
- `PROD_HOST`: The hostname for the production environment (e.g., "cart-order-service").
- `PROD_PORT`: The port number for the production environment (e.g., "8080").
- `PRODUCT_SERVICE_URL`: The URL for the product service (e.g., "http://product-service:4000").
- `DB_KIND`: The type of database being used (e.g., "postgresql").
- `DB_USERNAME`: The username for the database (e.g., "postgres").
- `DB_PASSWORD`: The password for the database (e.g., "postgres").
- `DB_JDBC_URL`: The JDBC URL for the database connection (e.g., "jdbc:postgresql://postgres-cart-order-service:5432/cart_order_service").

## Prerequisites for Production

For running the application in production, it is mandatory to have both RabbitMQ and a database configured and running. Ensure that the environment variables listed above are properly set to connect to these services.



