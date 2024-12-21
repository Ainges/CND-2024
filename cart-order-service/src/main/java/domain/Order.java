package domain;

import jakarta.persistence.*;

@Entity
public class Order {

    @Id
    private Long id;

    @Column(nullable = false)
    private long userId;

    @OneToOne
    @JoinColumn(nullable = false)
    private Cart cart;

    @Column(nullable = false)
    private OrderStatus status;

    public Order(OrderStatus status) {
        this.status = status;
    }

    public Order(Long id, long userId, Cart cart, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.cart = cart;
        this.status = status;
    }

    public Order() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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
}
