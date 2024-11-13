package repository;

import dto.cart.CartCreateDto;
import entity.Cart;
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
    public void addCart(CartCreateDto cartCreateDto) {

        Cart cart = new Cart();
        cart.setUserId(cartCreateDto.getUserId());

        persist(cart);
    }

}
