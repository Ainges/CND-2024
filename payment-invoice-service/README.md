# payment-invoice-service

This project is a .NET service that communicates with a RabbitMQ server and a PostgreSQL database. The service handles payment and invoice processing by consuming messages from RabbitMQ queues and interacting with the PostgreSQL database. The service requires several environment variables for configuring the RabbitMQ server and database connection. These environment variables must be set for the service to run properly.

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
