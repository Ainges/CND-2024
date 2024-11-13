package application;

import domain.Cart;
import infrastructure.web.dto.CartCreateDto;

import java.util.List;

public interface CartService {
    List<Cart> getAllCarts();
    Cart getCartById(long id);
    Cart addCart(CartCreateDto cartCreateDto);
}
