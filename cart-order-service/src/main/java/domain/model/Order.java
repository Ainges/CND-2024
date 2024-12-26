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
    private List<OrderPosition> orderPosition;


    public Order(long id, String userId, Cart cart, OrderStatus status, List<OrderPosition> orderPosition) {
        this.id = id;
        this.userId = userId;
        this.cart = cart;
        this.status = status;
        this.orderPosition = orderPosition;
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

    public List<OrderPosition> getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(List<OrderPosition> orderPosition) {
        this.orderPosition = orderPosition;
    }
}
