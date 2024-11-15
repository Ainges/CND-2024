package domain

type Comment struct {
    ID        int
    ProductID int
    UserID    int
    Content   string
}

type CommentRepository interface {
    Save(comment Comment) error
    GetByProductID(productID int) ([]Comment, error)
}
