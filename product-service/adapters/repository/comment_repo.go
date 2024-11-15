package repository

import (
	"database/sql"
	"product-service/domain"
)

type CommentRepo struct {
	DB *sql.DB
}

func NewCommentRepo(db *sql.DB) *CommentRepo {
	return &CommentRepo{DB: db}
}

func (r *CommentRepo) Save(comment domain.Comment) error {
	query := "INSERT INTO comments (product_id, user_id, content) VALUES (?, ?, ?)"
	_, err := r.DB.Exec(query, comment.ProductID, comment.UserID, comment.Content)
	return err
}

func (r *CommentRepo) GetByProductID(productID int) ([]domain.Comment, error) {
	query := "SELECT id, product_id, user_id, content FROM comments WHERE product_id = ?"
	rows, err := r.DB.Query(query, productID)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var comments []domain.Comment
	for rows.Next() {
		var comment domain.Comment
		if err := rows.Scan(&comment.ID, &comment.ProductID, &comment.UserID, &comment.Content); err != nil {
			return nil, err
		}
		comments = append(comments, comment)
	}

	return comments, nil
}
