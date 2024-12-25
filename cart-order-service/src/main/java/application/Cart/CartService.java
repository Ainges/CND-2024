package application.Cart;

import domain.model.Cart;
import domain.model.CartItem;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts();
    Cart getCartById(long id);
    Cart addCartItemToCart(String userId, String productId, int quantity);
    Cart setCartItemQuantity(String userId, String productId, int quantity);
    Cart removeCartItemFromCart(String userId, String productId);
    Cart clearCart(String userId);
    Cart checkout(String userId);
}
