package domain.model;

import adapter.jpa.entities.CartEntity;
import adapter.jpa.entities.CartItemEntity;
import adapter.jpa.entities.OrderEntity;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private long id;
    private String userId;
    private Cart cart;
    private OrderStatus status;

    public Order(long id, String userId, Cart cart, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.cart = cart;
        this.status = status;
    }

    public Order() {
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderEntity toOrderEntity() {

        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(this.cart.getId());
        cartEntity.setUserId(this.cart.getUserId());

        List<CartItemEntity> cartItemEntityList = new ArrayList<>();
        for (CartItem cartItem : this.cart.getCartItems()) {
            CartItemEntity cartItemEntity = new CartItemEntity();
            cartItemEntity.setProductId(cartItem.getProductId());
            cartItemEntity.setQuantity(cartItem.getQuantity());
            cartItemEntityList.add(cartItemEntity);
        }

        cartEntity.setCartItems(cartItemEntityList);
        cartEntity.setStatus(this.cart.getStatus());


        return new OrderEntity(this.id, this.userId, cartEntity, this.status);
    }
}
