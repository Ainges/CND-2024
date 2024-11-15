package adapters

import (
	"encoding/json"
	"fmt"
	"io"
	"net/http"
	"strings"
)

type UserServiceClient struct {
	BaseURL string
}

func NewUserServiceClient(baseURL string) *UserServiceClient {
	return &UserServiceClient{BaseURL: baseURL}
}

type UserVerificationResponse struct {
	FirstName string `json:"first_name"`
	LastName  string `json:"last_name"`
	IsAdmin   bool   `json:"is_admin"`
}

// VerifyToken sendet das Access-Token an den User-Service und überprüft den Benutzer
func (c *UserServiceClient) VerifyToken(token string) (*UserVerificationResponse, error) {
	url := fmt.Sprintf("%s/verify", c.BaseURL)
	req, _ := http.NewRequest("GET", url, nil)

	// Entferne zusätzliche Anführungszeichen um den Token
	token = strings.Trim(token, "\"")

	// Prüfe, ob das Präfix "Bearer " fehlt, und füge es bei Bedarf hinzu
	if !strings.HasPrefix(token, "Bearer ") {
		token = "Bearer " + token
	}

	// Setze den Authorization-Header
	req.Header.Set("Authorization", token)
	fmt.Printf("Authorization Header sent: %s\n", req.Header)

	res, err := http.DefaultClient.Do(req)
	if err != nil {
		fmt.Printf("Error sending request to User-Service: %v\n", err)
		return nil, fmt.Errorf("error connecting to user service: %v", err)
	}
	defer res.Body.Close()

	// Überprüfe den Statuscode der Antwort
	if res.StatusCode != http.StatusOK {
		body, _ := io.ReadAll(res.Body)
		fmt.Printf("Error response from User-Service, Status Code: %d, Body: %s\n", res.StatusCode, string(body))
		return nil, fmt.Errorf("unauthorized or invalid token")
	}

	var userResp UserVerificationResponse
	if err := json.NewDecoder(res.Body).Decode(&userResp); err != nil {
		return nil, fmt.Errorf("error decoding user service response: %v", err)
	}

	return &userResp, nil
}
