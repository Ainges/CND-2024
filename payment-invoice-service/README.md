# payment-invoice-service

# Prerequisites

Before running the application locally, make sure you have the following installed:

## 1. .NET 9 (Required only for local setup without Docker)
If you want to run the application **without Docker**, you must install **.NET 9**. You can download it from the official Microsoft website:
- [.NET 9 Download](https://dotnet.microsoft.com/download/dotnet/9.0)

## 2. Docker (Required only if you want to use Docker)
If you want to use **Docker** to run RabbitMQ and the database locally, you will need **Docker**. You can download it from the official website:
- [Docker](https://www.docker.com/)

# Running the Application Locally

## Development Mode

To run the application in development mode, use the following command:

```bash
dotnet run
```

## Production Mode

To run the application in production mode, you need to publish it first. Use the following command to publish the application:

```bash
dotnet publish -c Release -o ./out
```

This will create the output in the `./out` directory. You can then run the application using:

```bash
./out/payment-invoice-service
```

## Environment Variables

The service requires the following environment variables:

### RabbitMQ Configuration:
- **RABBITMQ_HOST**: The hostname of the RabbitMQ server (e.g., `rabbitmq`).
- **RABBITMQ_PORT**: The port of the RabbitMQ server (e.g., `5672`).
- **RABBITMQ_USERNAME**: The username for RabbitMQ authentication (e.g., `guest`).
- **RABBITMQ_PASSWORD**: The password for RabbitMQ authentication (e.g., `guest`).
- **RABBITMQ_HTTP_PORT**: The HTTP port for RabbitMQ management (e.g., `15672`).
- **RABBITMQ_QUEUE_NAME_PENDING_ORDERS**: The name of the queue for pending orders (e.g., `pending-orders`).
- **RABBITMQ_EXCHANGE_NAME**: The name of the RabbitMQ exchange (e.g., `my-exchange`).

### Database Configuration:
- **DB_HOST**: The hostname of the database server (e.g., `postgres-payment-invoice-service`).
- **DB_PORT**: The port of the database (e.g., `5432`).
- **DB_NAME**: The name of the database (e.g., `payment_invoice_service`).
- **DB_USERNAME**: The username for the database authentication (e.g., `postgres`).
- **DB_PASSWORD**: The password for the database authentication (e.g., `postgres`).

## Example `.env` File

You can store the environment variables in a `.env` file. Example:

```dotenv
RABBITMQ_HOST=rabbitmq
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
RABBITMQ_HTTP_PORT=15672
RABBITMQ_QUEUE_NAME_PENDING_ORDERS=pending-orders
RABBITMQ_EXCHANGE_NAME=my-exchange

DB_HOST=postgres-payment-invoice-service
DB_PORT=5432
DB_NAME=payment_invoice_service
DB_USERNAME=postgres
DB_PASSWORD=postgres
