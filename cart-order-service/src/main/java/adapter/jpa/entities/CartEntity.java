package adapter.jpa.entities;

import domain.model.Cart;
import domain.model.CartItem;
import domain.model.CartStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class CartEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;


    @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<CartItemEntity> cartItemEntities;

    private CartStatus status;

    @JoinColumn(nullable = true)
    @OneToOne
    private OrderEntity orderEntity;


    public CartEntity() {
        this.cartItemEntities = new ArrayList<CartItemEntity>();
    }

    public CartEntity(long id, String userId, List<CartItemEntity> cartItemEntities, CartStatus status, OrderEntity orderEntity) {
        this.id = id;
        this.userId = userId;
        this.cartItemEntities = cartItemEntities;
        this.status = status;
        this.orderEntity = orderEntity;
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

    public List<CartItemEntity> getCartItems() {
        return cartItemEntities;
    }

    public void setCartItems(List<CartItemEntity> cartItemEntities) {
        this.cartItemEntities = cartItemEntities;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public OrderEntity getOrder() {
        return orderEntity;
    }

    public void setOrder(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public void addCartItem(CartItemEntity cartItemEntity) {
        this.cartItemEntities.add(cartItemEntity);
    }

    public void removeCartItem(String productId) {
        this.cartItemEntities.removeIf(cartItemEntity -> cartItemEntity.getProductId().equals(productId));
    }

    public Cart toCart() {

        List<CartItemEntity> cartItemEntities = this.getCartItems();
        List<CartItem> cartItems = this.cartItemEntities.stream().map(CartItemEntity::toCartItem).collect(Collectors.toCollection(ArrayList::new));;

        if(this.orderEntity == null) {
            return (new Cart(this.id, this.userId, cartItems, this.status, null));
        }

        return (new Cart(this.id, this.userId, cartItems, this.status, this.orderEntity.toOrder()));




    }
}
