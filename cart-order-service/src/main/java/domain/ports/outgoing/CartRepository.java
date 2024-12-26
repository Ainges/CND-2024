package domain.ports.outgoing;

import domain.model.Cart;
import domain.model.CartItem;
import domain.model.CartStatus;
import domain.model.Order;

import java.util.List;

public interface CartRepository {

    public List<Cart> getAllCarts();
    public Cart getCartById(long id);
    public Cart getCurrentCartByUserId(String userId);
    public List<Cart> getCartsByUserId(String userId);
    public Cart addCartItemToCart(String userId, CartItem cartItem);
    public Cart removeCartItemFromCart(String userId, CartItem cartItem);
    public Cart clearCart(String userId);
    public List<CartItem> getAllCartItemsOfCart(long cartId);
    public Cart save(Cart cart);
    public Cart changeStatus(long cartId, CartStatus cartStatus);
    public Cart setOrder(long cartId, Order order);
    public CartItem getCartItemById(long id);
    public CartItem increaseCartItemQuantity(long cartItemId, int quantity);
    public CartItem decreaseCartItemQuantity(long cartItemId, int quantity);
    public CartItem setCartItemQuantity(long cartItemId, int quantity);

}
