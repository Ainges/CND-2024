package domain

type Product struct {
    ID          int
    Name        string
    Price       float64
    Description string
    Comments    []Comment
}

type ProductRepository interface {
    Save(product Product) error
    Update(product Product) error
    GetByID(id int) (*Product, error)
    GetAll() ([]Product, error)
}
