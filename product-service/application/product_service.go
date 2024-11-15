package application

import (
	"errors"
	"fmt"
	"product-service/adapters"
	"product-service/domain"
)

type ProductService struct {
	ProductRepo domain.ProductRepository
	UserClient  *adapters.UserServiceClient
}

// AddProduct fügt ein neues Produkt hinzu, wenn der Benutzer ein Admin ist
func (s *ProductService) AddProduct(product domain.Product, token string) error {
	// Überprüfe das Token beim User-Service
	userResp, err := s.UserClient.VerifyToken(token)

	if err != nil {
		return errors.New("unauthorized: invalid token")
	}

	// Prüfe, ob der Benutzer ein Admin ist
	if !userResp.IsAdmin {
		return errors.New("only admins can add products")
	}

	// Optional: Logge den Benutzer, der das Produkt hinzufügt
	fmt.Printf("Adding product by admin: %s %s\n", userResp.FirstName, userResp.LastName)

	// Produkt speichern
	return s.ProductRepo.Save(product)
}

// GetProduct ruft ein Produkt basierend auf der ID ab
func (s *ProductService) GetProduct(id int) (*domain.Product, error) {
	product, err := s.ProductRepo.GetByID(id)
	if err != nil {
		return nil, errors.New("product not found")
	}
	return product, nil
}

// GetAllProducts ruft alle Produkte ab
func (s *ProductService) GetAllProducts() ([]domain.Product, error) {
	products, err := s.ProductRepo.GetAll()
	if err != nil {
		return nil, errors.New("failed to retrieve products")
	}
	return products, nil
}
