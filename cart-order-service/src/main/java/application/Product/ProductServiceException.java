package application.Product;

public class ProductServiceException extends RuntimeException {
    public ProductServiceException(String message, Exception e) {
        super(message);
    }
}
