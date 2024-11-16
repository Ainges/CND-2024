package infrastructure.repository.Cart;

public class CartRepositoryException extends RuntimeException {
    public CartRepositoryException(String message, Exception e) {
        super(message);
    }
}
