package application.Cart;

import domain.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts();
    Cart getCartById(long id);
    Cart addCartItemToCart(long userId, long productId);
    Cart removeCartItemFromCart(long userId, long productId);
    Cart clearCart(long userId);
    Cart checkout(long userId);
}
