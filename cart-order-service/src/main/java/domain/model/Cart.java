package domain.model;

import adapter.jpa.entities.CartItemEntity;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private long id;
    private String userId;
    private List<CartItem> cartItems;
    private CartStatus status;
    private Order order;

    public Cart(long id, String userId, List<CartItem> cartItems, CartStatus status, Order order) {
        this.id = id;
        this.userId = userId;
        this.cartItems = cartItems;
        this.status = status;
        this.order = order;
    }

    public Cart (String userId) {
        this.cartItems = new ArrayList<>();
        this.userId = userId;
        this.status = CartStatus.OPEN;
    }

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public void removeCartItem(String productId) {
        cartItems.removeIf(cartItem -> cartItem.getProductId().equals(productId));
    }
}

