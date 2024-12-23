package domain.model;

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
}
