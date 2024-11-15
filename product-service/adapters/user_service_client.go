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

func (c *UserServiceClient) VerifyToken(token string) (*UserVerificationResponse, error) {
	url := fmt.Sprintf("%s/verify", c.BaseURL)
	req, _ := http.NewRequest("GET", url, nil)

	// Entferne zusätzliche Anführungszeichen
	token = strings.Trim(token, "\"")

	// Prüfe, ob das Präfix fehlt, und füge es hinzu
	if !strings.HasPrefix(token, "Bearer ") {
		token = "Bearer " + token
	}

	// Setze den Authorization-Header
	req.Header.Set("Authorization", token)
	fmt.Printf("Authorization Header sent: %s\n", req.Header)

	res, err := http.DefaultClient.Do(req)
	if err != nil || res.StatusCode != http.StatusOK {
		body, _ := io.ReadAll(res.Body)
		fmt.Printf("Response Body: %s\n", string(body)) // Logge die Antwort
		return nil, fmt.Errorf("unauthorized or invalid token")
	}
	defer res.Body.Close()

	var userResp UserVerificationResponse
	if err := json.NewDecoder(res.Body).Decode(&userResp); err != nil {
		return nil, err
	}

	return &userResp, nil
}
