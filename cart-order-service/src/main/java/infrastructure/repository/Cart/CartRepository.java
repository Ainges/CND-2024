package infrastructure.repository.Cart;

import domain.Cart;
import domain.Product;
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


    public Cart addProductToCart(long userId, Product product) {
        Cart cart = getCartByUserId(userId);
        cart.addProduct(product);
        return cart;
    }

    private Cart getCartByUserId(long userId) {
        Cart cart = find("userId", userId).firstResult();
        if(cart == null) {
            addCartForUser(userId);
        }
        return cart;
    }
}
