## Running the application locally

### Development Mode

To run the application in development mode, which enables live coding, use the following command:

```shell script
./mvnw compile quarkus:dev
```

In development mode, Quarkus provides a Dev UI that is accessible at <http://localhost:8080/q/dev/>.

### Production Mode

To run the application in production mode, you need to package it first. Use the following command to package the application:

```shell script
./mvnw package
```

This will create a `quarkus-run.jar` file in the `target/quarkus-app/` directory. You can then run the application using:

```shell script
java -jar target/quarkus-app/quarkus-run.jar
```
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







# cart-order-service

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/cart-order-service-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST resources for Hibernate ORM with Panache ([guide](https://quarkus.io/guides/rest-data-panache)): Generate Jakarta REST resources for your Hibernate Panache entities and repositories
- Camel OpenAPI Java ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/openapi-java.html)): Expose OpenAPI resources defined in Camel REST DSL
- Camel Core ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/core.html)): Camel core functionality and basic Camel languages: Constant, ExchangeProperty, Header, Ref, Simple and Tokenize
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- Camel REST OpenApi ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/rest-openapi.html)): To call REST services using OpenAPI specification as contract
- Camel Rest ([guide](https://camel.apache.org/camel-quarkus/latest/reference/extensions/rest.html)): Expose REST services and their OpenAPI Specification or call external REST services

## Provided Code

### REST Data with Panache

Generating Jakarta REST resources with Panache

[Related guide section...](https://quarkus.io/guides/rest-data-panache)

