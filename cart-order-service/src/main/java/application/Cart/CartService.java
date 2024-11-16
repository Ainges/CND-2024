package application.Cart;

import domain.Cart;
import infrastructure.web.dto.CartCreateDto;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts();
    Cart getCartById(long id);
    Cart addProductToCart(long userId, long productId);
    Cart removeProductFromCart(long userId, long productId);
    Cart clearCart(long userId);
    Cart checkout(long userId);
}
