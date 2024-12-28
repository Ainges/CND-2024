// src/main/java/adapter/jpa/repositories/CartRepository.java
package adapter.jpa.repositories;

import adapter.jpa.entities.CartEntity;
import adapter.jpa.entities.CartItemEntity;
import domain.model.*;
import domain.ports.outgoing.CartRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class JpaCartRepository implements CartRepository,PanacheRepository<CartEntity> {


    private static final Logger logger = LoggerFactory.getLogger(JpaCartRepository.class);


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

        // Find cartEntity by userId but only when Status is OPEN
        CartEntity cartEntity = find("userId = ?1 and status = ?2", userId, CartStatus.OPEN).firstResult();

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
        CartEntity cartEntity = find("userId = ?1 and status = ?2", userId, CartStatus.OPEN).firstResult();

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
        CartEntity cartEntity = find("userId = ?1 and status = ?2", userId, CartStatus.OPEN).firstResult();
        cartEntity.getCartItems().clear();
        return cartEntity.toCart();
    }

    @Override
    public CartItem getCartItemById(long id) {
        CartItemEntity cartItemEntity = getEntityManager().find(CartItemEntity.class, id);
        return cartItemEntity.toCartItem();
    }

    @Override
    @Transactional
    public CartItem increaseCartItemQuantity(long cartItemId, int quantity) {

        if(quantity < 0){
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if(cartItemId <= 0){
            throw new IllegalArgumentException("CartItemId must be greater than 0");
        }

        CartItemEntity cartItemEntity = getEntityManager().find(CartItemEntity.class, cartItemId);
        cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
        getEntityManager().persist(cartItemEntity);
        return cartItemEntity.toCartItem();
    }

    @Override
    public CartItem decreaseCartItemQuantity(long cartItemId, int quantity) {
        CartItemEntity cartItemEntity = getEntityManager().find(CartItemEntity.class, cartItemId);
        cartItemEntity.setQuantity(cartItemEntity.getQuantity() - quantity);
        getEntityManager().persist(cartItemEntity);
        return cartItemEntity.toCartItem();
    }

    @Override
    public CartItem setCartItemQuantity(long cartItemId, int quantity) {
        CartItemEntity cartItemEntity = getEntityManager().find(CartItemEntity.class, cartItemId);
        cartItemEntity.setQuantity(quantity);
        getEntityManager().persist(cartItemEntity);
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
     *
     * This method updates the cart in the database, but only non collection fields are updated.
     * The collection fields are need to be updated in the respective methods.
     */
    @Override
    @Transactional
    public Cart changeStatus(long cartId, CartStatus cartStatus) {
        CartEntity cartEntity = findById(cartId);
        cartEntity.setStatus(cartStatus);

        return cartEntity.toCart();
    }

    @Override
    public Cart setOrder(long cartId, Order order) {

        Cart cart = getCartById(cartId);

        if (cart.getOrder() != null) {
            throw new JpaCartRepositoryException("Cart already has an order");
        }

        cart.setOrder(order);
        return cart;

    }
}