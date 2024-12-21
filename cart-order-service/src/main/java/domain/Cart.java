package domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private long userId;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<CartItem> cartItems;

    private CartStatus status;

    @JoinColumn(nullable = true)
    @OneToOne
    private Order order;


    public Cart() {
    }

    public Cart(Long id, long userId, List<CartItem> cartItems, CartStatus status, Order order) {
        this.id = id;
        this.userId = userId;
        this.cartItems = cartItems;
        this.status = status;
        this.order = order;
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

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
