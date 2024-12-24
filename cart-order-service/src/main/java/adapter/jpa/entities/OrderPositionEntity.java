package adapter.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class OrderPositionEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = true)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal priceInEuroCents;

    public OrderPositionEntity() {
    }

    public OrderPositionEntity(long id, String productId, String productName, int quantity, BigDecimal priceInEuroCents) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.priceInEuroCents = priceInEuroCents;
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
}
