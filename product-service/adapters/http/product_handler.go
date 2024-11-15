// adapters/http/product_handler.go

package http

import (
	"net/http"
	"product-service/application"
	"product-service/domain"
	"strconv"

	"github.com/gin-gonic/gin"
)

type ProductHandler struct {
	ProductService *application.ProductService
	CommentService *application.CommentService
}

// NewProductHandler ist ein Konstruktor für ProductHandler
func NewProductHandler(productService *application.ProductService, commentService *application.CommentService) *ProductHandler {
	return &ProductHandler{
		ProductService: productService,
		CommentService: commentService,
	}
}

func (h *ProductHandler) AddProduct(c *gin.Context) {
	var product domain.Product

	if err := c.ShouldBindJSON(&product); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	token := c.GetHeader("Authorization")
	if token == "" {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "Authorization token required"})
		return
	}

	if err := h.ProductService.AddProduct(product, token); err != nil {
		c.JSON(http.StatusForbidden, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Product added successfully"})
}

func (h *ProductHandler) GetProduct(c *gin.Context) {
	idParam := c.Param("id")
	id, err := strconv.Atoi(idParam)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid product ID"})
		return
	}

	product, err := h.ProductService.GetProduct(id)
	if err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, product)
}

func (h *ProductHandler) AddComment(c *gin.Context) {
	var comment domain.Comment

	if err := c.ShouldBindJSON(&comment); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.CommentService.AddComment(comment); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Comment added successfully"})
}
