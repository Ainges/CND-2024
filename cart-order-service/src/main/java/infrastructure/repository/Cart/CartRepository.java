package infrastructure.repository.Cart;

import domain.Cart;
import domain.CartStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CartRepository implements PanacheRepository<Cart> {

    public List<Cart> getAllCarts() {
        return listAll();
    }

    public Cart getCartById(long id) {
        Cart cart =  find("id", id).firstResult();

        if(cart == null) {
            throw new IllegalArgumentException("Cart with id " + id + " not found");
        }
        return cart;

    }

    @Transactional
    public void addCartForUser(long userId) throws CartRepositoryException {

        Cart cart = new Cart();
        cart.setUserId(userId);

        try {
            persist(cart);
        }
        catch (Exception e) {
            throw new CartRepositoryException("Failed to addCartForUser cart", e);
        }
    }
    @Transactional
    public void deleteCart(long id) {
        delete("id", id);
    }


    public Cart addCartItemToCart() {
        //TODO: implement
        return null;
    }

    private Cart getOpenCartByUserId(long userId) {
        Cart cart = find("userId = ?1 and status = ?2", userId, CartStatus.OPEN).firstResult();
        if(cart == null) {
            addCartForUser(userId);
        }
        return cart;
    }

    private List<Cart> getCartsByUserId(long userId) {
        List<Cart> cartList =  find("userId", userId).list();
        if(cartList.isEmpty()) {
            addCartForUser(userId);
        }
        return cartList;
    }
}
