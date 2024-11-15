# Base image with Go
FROM golang:1.18

# Set the working directory in the container
WORKDIR /app

# Install SQLite3 CLI
RUN apt-get update && apt-get install -y sqlite3 && apt-get clean

# Copy go.mod and go.sum files, then download dependencies
COPY go.mod go.sum ./
RUN go mod download

# Copy the rest of the application code
COPY . .

# Build the Go application
RUN go build -o product-service ./cmd/main.go

# Copy the SQL file directly into the working directory
COPY init.sql .

# Expose the port the app runs on
EXPOSE 8081

# Environment variables for configuration (can be overridden at runtime)
ENV USER_SERVICE_URL=http://user-service:5000
ENV DATABASE_URL=sqlite3://./product.db

# Command to initialize the database and start the application
CMD ["sh", "-c", "sqlite3 product.db < init.sql && ./product-service"]
