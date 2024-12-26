package adapter.jpa.entities;

import domain.model.Order;
import domain.model.OrderPosition;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class OrderPositionEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int orderPosition;

    @Column(nullable = true)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal priceInEuroCents;

    @ManyToOne
    private OrderEntity orderEntity;

    public OrderPositionEntity() {
    }

    public OrderPositionEntity(long id, String productId, String productName, int quantity, BigDecimal priceInEuroCents, OrderEntity orderEntity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.priceInEuroCents = priceInEuroCents;
        this.orderEntity = orderEntity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(int orderPosition) {
        this.orderPosition = orderPosition;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceInEuroCents() {
        return priceInEuroCents;
    }

    public void setPriceInEuroCents(BigDecimal priceInEuroCents) {
        this.priceInEuroCents = priceInEuroCents;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public OrderPosition toOrderPosition() {
        return new OrderPosition(id, orderPosition, productId, productName, quantity, priceInEuroCents);
    }
}
