package domain.model;

import java.math.BigDecimal;

public class OrderPosition {

    private long id;
    private String productId;
    private int quantity;
    private BigDecimal priceInEuroCents;

    public OrderPosition(long id, String productId, int quantity, BigDecimal priceInEuroCents) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.priceInEuroCents = priceInEuroCents;
    }

    public OrderPosition() {
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
