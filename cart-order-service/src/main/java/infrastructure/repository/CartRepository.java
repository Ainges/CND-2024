package infrastructure.repository;

import domain.Cart;
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
        return find("id", id).firstResult();
    }

    @Transactional
    public void addCart(Cart cart) {
        persist(cart);
    }


}
