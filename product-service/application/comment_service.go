package application

import "product-service/domain"

type CommentService struct {
    CommentRepo domain.CommentRepository
}

func (s *CommentService) AddComment(comment domain.Comment) error {
    return s.CommentRepo.Save(comment)
}

func (s *CommentService) GetCommentsByProduct(productID int) ([]domain.Comment, error) {
    return s.CommentRepo.GetByProductID(productID)
}
