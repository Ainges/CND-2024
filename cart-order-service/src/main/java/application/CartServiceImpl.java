package application;

import domain.Cart;
import infrastructure.repository.CartRepository;
import infrastructure.web.dto.CartCreateDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class CartServiceImpl implements CartService {

    @Inject
    CartRepository cartRepository;

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    public List<Cart> getAllCarts() {
        return List.of();
    }

    @Override
    public Cart getCartById(long id) {
        return null;
    }

    @Override
    public Cart addCart(CartCreateDto cartCreateDto) {
        Cart cart = new Cart();
        cart.setUserId(cartCreateDto.getUserId());
        cartRepository.addCart(cart);
        logger.info("Created cart with id: {}", cart.getId());
        logger.debug("Created cart: {}", cart);
        return cart;





    }
}
