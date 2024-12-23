package application.Cart;

import domain.model.Cart;
import domain.model.CartItem;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts();
    Cart getCartById(long id);
    Cart addCartItemToCart(String userId, String productId);
    Cart removeCartItemFromCart(long userId, String productId);
    Cart clearCart(long userId);
    Cart checkout(long userId);
}
