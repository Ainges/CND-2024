package main

import (
	"database/sql"
	"product-service/adapters"
	"product-service/adapters/http"
	"product-service/adapters/repository"
	"product-service/application"

	"github.com/gin-gonic/gin"
	_ "github.com/mattn/go-sqlite3"
)

func main() {
	db, err := sql.Open("sqlite3", "./product.db")
	if err != nil {
		panic(err)
	}
	defer db.Close()

	productRepo := repository.NewProductRepo(db)
	commentRepo := repository.NewCommentRepo(db)
	userClient := adapters.NewUserServiceClient("http://localhost:5000") // User Service URL

	productService := &application.ProductService{ProductRepo: productRepo, UserClient: userClient}
	commentService := &application.CommentService{CommentRepo: commentRepo}

	r := gin.Default()

	// Verwende den Konstruktor für ProductHandler und übergebe beide Services
	productHandler := http.NewProductHandler(productService, commentService)

	r.POST("/products", productHandler.AddProduct)
	r.GET("/products/:id", productHandler.GetProduct)
	r.POST("/products/:id/comments", productHandler.AddComment)

	r.Run(":8081")
}
