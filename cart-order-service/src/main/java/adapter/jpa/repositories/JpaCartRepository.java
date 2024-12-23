// src/main/java/adapter/jpa/repositories/CartRepository.java
package adapter.jpa.repositories;

import adapter.jpa.entities.CartEntity;
import adapter.jpa.entities.CartItemEntity;
import domain.model.Cart;
import domain.model.CartItem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class JpaCartRepository implements domain.ports.outgoing.CartRepository,PanacheRepository<CartEntity> {


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
            cartEntity = new CartEntity();
            cartEntity.setUserId(userId);
            cartEntity.setStatus(domain.model.CartStatus.OPEN);
            persist(cartEntity);
            return cartEntity.toCart();
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


        cartEntity.addCartItem(cartItemEntity);
        persist(cartEntity);
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
    @Transactional
    public Cart save(Cart cart) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(cart.getId());
        cartEntity.setUserId(cart.getUserId());
        cartEntity.setStatus(cart.getStatus());
        cartEntity.setOrder(null);

        List<CartItemEntity> cartItemEntities = new ArrayList<>();
        cartEntity.setCartItems(cartItemEntities);
        // Überprüfen, ob die Entität bereits existiert
        persist(cartEntity);

        return cartEntity.toCart();

    }
}