package application.Cart;


import domain.Cart;
import infrastructure.repository.Cart.CartRepository;
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


        return cartRepository.getAllCarts();

    }

    @Override
    public Cart getCartById(long id) {

        Cart cart = new Cart();

        try {
            cart = cartRepository.getCartById(id);
        }
        catch (Exception e) {
            logger.error("Failed to get cart with id: {}", id);
            throw new CartServiceException("Failed to get cart", e);
        }


        return cart;
    }

    @Override
    public Cart addCartItemToCart(long userId, long productId) {

        Cart cart = new Cart();

        // first check if user has a cart
        if(!userHasCart(userId)) {
            try {
                cart = createCartForUser(userId);
            }
            catch (Exception e) {
                logger.error("Failed to create cart for user with id: {}", userId);
                throw new CartServiceException("Failed to create cart for user with id: " + userId);
            }

        }
        // next check if cartItem


        // add cartItem to cart

        return cart;


    }

    @Override
    public Cart removeCartItemFromCart(long userId, long productId) {
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

    public Cart deleteCart(long id) {

        Cart cart;
        try {
            cart = getCartById(id);
        }
        catch (Exception e) {
            logger.info("Cart with id: {} already deleted or does not exist", id);
            throw new CartServiceException("Cart already deleted or does not exist");
        }

        try{
            cartRepository.deleteCart(cart.getId());
        }
        catch (Exception e){
            logger.error("Failed to delete cart with id: {}", id);
            throw new CartServiceException("Failed to delete cart", e);
        }
        return cart;
    }

    public boolean userHasCart(long userId) {

       Cart cart = cartRepository.find("userId", userId).firstResult();

        // simpler to understand like this?
        //noinspection RedundantIfStatement
        if(cart == null) {
           return false;
       }
         return true;

    }


    /**
     * Creates a new cart for a user.
     * Throws a CartServiceException if the cart could not be created.
     *
     * @param userId the user id
     * @return the created cart
     * @throws CartServiceException if the cart could not be created
     */
    public Cart createCartForUser(long userId) throws CartServiceException {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cartRepository.addCartForUser(userId);
        if(cart.getId() == null) {
            logger.error("Failed to create cart for user with id: {}", userId);
            throw new CartServiceException("Failed to create cart for user with id: " + userId);
        }

        return cart;

    }

    public boolean productExists(long productId) {

        return false;
    }
}
