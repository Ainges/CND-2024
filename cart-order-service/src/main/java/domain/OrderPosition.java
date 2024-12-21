package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class OrderPosition {

    @Id
    private Long id;

    @Column(nullable = false)
    private long productId;

    @Column(nullable = true)
    private String productName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal priceInEuroCents;

    public OrderPosition() {
    }

    public OrderPosition(Long id, long productId, String productName, int quantity, BigDecimal priceInEuroCents) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.priceInEuroCents = priceInEuroCents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
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
