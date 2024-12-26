package domain.model;

import java.math.BigDecimal;

public class OrderPosition {

    private long id;
    private int orderPosition;
    private String productId;
    private String productName;
    private int quantity;
    private BigDecimal priceInEuroCents;

    public OrderPosition(long id, int orderPosition, String productId, String productName, int quantity, BigDecimal priceInEuroCents) {
        this.id = id;
        this.orderPosition = orderPosition;
        this.productId = productId;
        this.productName = productName;
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

    public int getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(int orderPosition) {
        this.orderPosition = orderPosition;
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
