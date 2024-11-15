// adapters/repository/product_repo.go
package repository

import (
	"database/sql"
	"product-service/domain"
)

type ProductRepo struct {
	DB *sql.DB
}

func NewProductRepo(db *sql.DB) *ProductRepo {
	return &ProductRepo{DB: db}
}

func (r *ProductRepo) Save(product domain.Product) error {
	query := "INSERT INTO products (name, price, description) VALUES (?, ?, ?)"
	_, err := r.DB.Exec(query, product.Name, product.Price, product.Description)
	return err
}

func (r *ProductRepo) Update(product domain.Product) error {
	query := "UPDATE products SET name = ?, price = ?, description = ? WHERE id = ?"
	_, err := r.DB.Exec(query, product.Name, product.Price, product.Description, product.ID)
	return err
}

func (r *ProductRepo) GetByID(id int) (*domain.Product, error) {
	query := "SELECT id, name, price, description FROM products WHERE id = ?"
	row := r.DB.QueryRow(query, id)

	var product domain.Product
	err := row.Scan(&product.ID, &product.Name, &product.Price, &product.Description)
	if err != nil {
		return nil, err
	}
	return &product, nil
}

func (r *ProductRepo) GetAll() ([]domain.Product, error) {
	query := "SELECT id, name, price, description FROM products"
	rows, err := r.DB.Query(query)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var products []domain.Product
	for rows.Next() {
		var product domain.Product
		if err := rows.Scan(&product.ID, &product.Name, &product.Price, &product.Description); err != nil {
			return nil, err
		}
		products = append(products, product)
	}

	return products, nil
}
