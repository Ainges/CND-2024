package application.Cart;


import adapter.jpa.repositories.JpaCartRepository;
import domain.model.Cart;
import domain.model.CartItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
    public Cart addCartItemToCart(String userId, String productId) {

        Cart cart  = jpaCartRepository.getCurrentCartByUserId(userId);

        if(cart == null){
            cart = new Cart(userId);
        }

        // check if the product is already in the cart
        for(CartItem cartItem : cart.getCartItems()){
            if(cartItem.getProductId().equals(productId)){
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                return jpaCartRepository.save(cart);
            }
        }

        CartItem cartItem = new CartItem(productId, 1);
        cart.addCartItem(cartItem);

        return jpaCartRepository.save(cart);
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
