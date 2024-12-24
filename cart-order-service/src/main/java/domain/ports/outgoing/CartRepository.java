package domain.ports.outgoing;

import adapter.jpa.entities.CartEntity;
import domain.model.Cart;
import domain.model.CartItem;

import java.util.List;

public interface CartRepository {

    public List<Cart> getAllCarts();
    public Cart getCartById(long id);
    public Cart getCurrentCartByUserId(String userId);
    public List<Cart> getCartsByUserId(String userId);
    public Cart addCartItemToCart(String userId, CartItem cartItem);
    public Cart removeCartItemFromCart(String userId, CartItem cartItem);
    public Cart clearCart(String userId);
    public Cart checkout(String userId);
    public CartItem getCartItemById(long id);
    public List<CartItem> getAllCartItemsOfCart(long cartId);
    public Cart save(Cart cart);
    public Cart update(Cart cart);
}
