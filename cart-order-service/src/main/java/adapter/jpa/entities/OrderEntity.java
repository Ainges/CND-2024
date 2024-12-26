package adapter.jpa.entities;

import domain.model.Order;
import domain.model.OrderPosition;
import domain.model.OrderStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderPositionEntity> orderPositionEntities;

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

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    public List<OrderPositionEntity> getOrderPositionEntities() {
        return orderPositionEntities;
    }

    public void setOrderPositionEntities(List<OrderPositionEntity> orderPositionEntities) {
        this.orderPositionEntities = orderPositionEntities;
    }

    public Order toOrder() {

        Order order = new Order();
        order.setId(this.id);
        order.setUserId(this.userId);
        order.setCart(this.cartEntity.toCart());
        order.setStatus(this.status);

        List<OrderPosition> orderPositions = new ArrayList<>();
        for (OrderPositionEntity orderPositionEntity : this.orderPositionEntities) {
            orderPositions.add(orderPositionEntity.toOrderPosition());
        }

        order.setOrderPosition(orderPositions);

        return order;

    }
}
