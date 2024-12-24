package adapter.jpa.entities;

import domain.model.Order;
import domain.model.OrderStatus;
import jakarta.persistence.*;

@Entity
public class OrderEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;

    @OneToOne
    @JoinColumn(nullable = false)
    private CartEntity cartEntity;

    @Column(nullable = false)
    private OrderStatus status;

    public OrderEntity(OrderStatus status) {
        this.status = status;
    }

    public OrderEntity(long id, String userId, CartEntity cartEntity, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.cartEntity = cartEntity;
        this.status = status;
    }

    public OrderEntity() {

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

    public CartEntity getCart() {
        return cartEntity;
    }

    public void setCart(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Order toOrder() {
        return new Order(this.id, this.userId, this.cartEntity.toCart(), this.status);
    }
}
