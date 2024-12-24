// src/main/java/adapter/jpa/repositories/CartRepository.java
package adapter.jpa.repositories;

import adapter.jpa.entities.CartEntity;
import adapter.jpa.entities.CartItemEntity;
import domain.model.Cart;
import domain.model.CartItem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class JpaCartRepository implements domain.ports.outgoing.CartRepository,PanacheRepository<CartEntity> {


    private final Logger logger;

    @jakarta.inject.Inject
    public JpaCartRepository(Logger logger) {
        this.logger = logger;
    }

    @Override
    public List<Cart> getAllCarts() {
        List<CartEntity> cartEntities = listAll();
        List<Cart> carts = new ArrayList<>();
        for (CartEntity cartEntity : cartEntities) {
            carts.add(cartEntity.toCart());
        }
        return carts;
    }

    @Override
    public Cart getCartById(long id) {
        CartEntity cartEntity = findById(id);
        return cartEntity.toCart();
    }

    @Override
    @Transactional
    public Cart getCurrentCartByUserId(String userId) {
        CartEntity cartEntity = find("userId", userId).firstResult();

        if(cartEntity == null){
            return null;
        }
        return cartEntity.toCart();
    }

    @Override
    public List<Cart> getCartsByUserId(String userId) {
        List<CartEntity> cartEntities = find("userId", userId).list();
        List<Cart> carts = new ArrayList<>();
        for (CartEntity cartEntity : cartEntities) {
            carts.add(cartEntity.toCart());
        }
        return carts;
    }

    @Override
    public Cart addCartItemToCart(String userId, CartItem cartItem) {
        CartEntity cartEntity = find("userId", userId).firstResult();

        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setProductId(cartItem.getProductId());
        cartItemEntity.setQuantity(cartItem.getQuantity());
        cartItemEntity.setCartEntity(cartEntity);

        cartEntity.addCartItem(cartItemEntity);
        getEntityManager().persist(cartItemEntity);

        return cartEntity.toCart();
    }

    @Override
    public Cart removeCartItemFromCart(String userId, CartItem cartItem) {
        CartEntity cartEntity = find("userId", userId).firstResult();
        cartEntity.removeCartItem(cartItem.getProductId());
        persist(cartEntity);
        return cartEntity.toCart();
    }

    @Override
    public Cart clearCart(String userId) {
        CartEntity cartEntity = find("userId", userId).firstResult();
        cartEntity.getCartItems().clear();
        persist(cartEntity);
        return cartEntity.toCart();
    }

    @Override
    public Cart checkout(String userId) {
        return null;
    }

    @Override
    public CartItem getCartItemById(long id) {
        CartItemEntity cartItemEntity = getEntityManager().find(CartItemEntity.class, id);
        return cartItemEntity.toCartItem();
    }

    @Override
    public List<CartItem> getAllCartItemsOfCart(long cartId) {

        CartEntity cartEntity = findById(cartId);
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemEntity cartItemEntity : cartEntity.getCartItems()) {
            cartItems.add(cartItemEntity.toCartItem());
        }
        return cartItems;
    }

    /**
     * Save the cart
     * @param cart
     * @return Cart
     *
     * This method saves the cart in the database. Collection fields are not saved in this method, but initialized with empty list.
     */
    @Override
    @Transactional
    public Cart save(Cart cart) {

        CartEntity cartEntity = new CartEntity();
        cartEntity.setUserId(cart.getUserId());
        cartEntity.setStatus(cart.getStatus());
        cartEntity.setOrder(null);

        // initialize with empty list
        List<CartItemEntity> cartItemEntities = new ArrayList<>();
        cartEntity.setCartItems(cartItemEntities);

        this.persist(cartEntity);
        logger.info("Creating cart with id: " + cartEntity.getId() + " for user: " + cartEntity.getUserId());
        return cartEntity.toCart();

    }

    /**
     * Update the cart
     * @param cart
     * @return Cart
     *
     * This method updates the cart in the database, but only non collection fields are updated.
     * The collection fields are need to be updated in the respective methods.
     */
    @Override
    @Transactional
    public Cart update(Cart cart) {
        CartEntity cartEntity = new CartEntity();

        //Update the CartEntity with the new values
        cartEntity.setId(cart.getId());
        cartEntity.setUserId(cart.getUserId());
        cartEntity.setStatus(cart.getStatus());

        // Convert Order to OrderEntity
        if(cart.getOrder() != null){
            // TODO: Set the orderEntity
            cart.setOrder(null);
        }


        return cartEntity.toCart();
    }
}