package config

import (
	"log"
	"os"
)

// Config struct holds all configuration values
type Config struct {
	DatabaseURL    string
	UserServiceURL string
}

// LoadConfig loads configuration values from environment variables
func LoadConfig() Config {
	dbURL := os.Getenv("DATABASE_URL")
	userServiceURL := os.Getenv("USER_SERVICE_URL")

	if dbURL == "" {
		dbURL = "sqlite3://./product.db" // Standardwert für die Datenbank
	}
	if userServiceURL == "" {
		userServiceURL = "http://localhost:5000" // Standard-URL für den User-Service
	}

	return Config{
		DatabaseURL:    dbURL,
		UserServiceURL: userServiceURL,
	}
}

func MustGetEnv(key string) string {
	value := os.Getenv(key)
	if value == "" {
		log.Fatalf("Environment variable %s is required", key)
	}
	return value
}
