package adapter.jpa.entities;

import domain.model.CartItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class CartItemEntity {


    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int quantity;

    public CartItemEntity() {
    }

    public CartItemEntity(long id, String productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
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

    public CartItem toCartItem() {

        return new CartItem(this.id, this.productId, this.quantity);
    }
}
