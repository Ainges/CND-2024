package application.Cart;


import adapter.jpa.repositories.JpaCartRepository;
import domain.model.Cart;
import domain.model.CartItem;
import domain.model.CartStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class CartServiceImpl implements CartService {

    @Inject
    JpaCartRepository jpaCartRepository;


    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);


    @Override
    public List<Cart> getAllCarts() {
        return jpaCartRepository.getAllCarts();
    }

    @Override
    public Cart getCartById(long id) {
        return jpaCartRepository.getCartById(id);
    }


    @Override
    @Transactional
    public Cart addCartItemToCart(String userId, String productId) {

        Cart cart = jpaCartRepository.getCurrentCartByUserId(userId);

        if(cart == null){
            cart = new Cart(userId);
            cart.setStatus(CartStatus.OPEN);
            cart = jpaCartRepository.save(cart);
        }

        CartItem cartItem = new CartItem(productId, 1);

        // Add the cart item to the cart;
        cart = jpaCartRepository.addCartItemToCart(userId, cartItem);

        return cart;
    }

    @Override
    public Cart removeCartItemFromCart(long userId, String productId) {
        return null;
    }

    @Override
    public Cart clearCart(long userId) {
        return null;
    }

    @Override
    public Cart checkout(long userId) {
        return null;
    }
}
