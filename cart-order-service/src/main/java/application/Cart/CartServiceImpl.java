package application.Cart;


import adapter.jpa.repositories.JpaCartRepository;
import adapter.jpa.repositories.JpaOrderRepository;
import domain.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CartServiceImpl implements CartService {

    @Inject
    JpaCartRepository jpaCartRepository;

    @Inject
    JpaOrderRepository jpaOrderRepository;



    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);


    @Override
    public List<Cart> getAllCarts() {
        return jpaCartRepository.getAllCarts();
    }

    @Override
    public Cart getCartById(long id) {

        Cart cart = new Cart();

        try {
            cart = jpaCartRepository.getCartById(id);
        }
        catch (Exception e) {
            throw new CartServiceException("Cart not found");
        }
        return cart;
    }

    @Override
    public Cart getCartByUserId(String userId) {

        Cart cart = null;
        try {
            cart = jpaCartRepository.getCurrentCartByUserId(userId);
        }
        catch (Exception e) {
            throw new CartServiceException("Cart not found");
        }

        if (cart == null) {
            throw new CartServiceException("User has no active cart");
        }
        return cart;

    }


    @Override
    @Transactional
    public Cart addCartItemToCart(String userId, String productId, int quantity) {

        Cart cart = jpaCartRepository.getCurrentCartByUserId(userId);

        //TODO: Check if Product exists in external service

        if(cart == null){
            cart = new Cart(userId);
            cart.setStatus(CartStatus.OPEN);
            cart = jpaCartRepository.save(cart);
        }

        if (cart.getCartItems().stream().anyMatch(cartItem -> cartItem.getProductId().equals(productId))) {
            logger.info("Product already in cart");

            CartItem cartItem = jpaCartRepository.getCartItemById(cart.getId());
            jpaCartRepository.increaseCartItemQuantity(cartItem.getId(), quantity);

            cart = jpaCartRepository.getCartById(cart.getId());

            return cart;
        }

        CartItem cartItem = new CartItem(productId, quantity);

        // Add the cart item to the cart;
        cart = jpaCartRepository.addCartItemToCart(userId, cartItem);

        return cart;
    }

    @Override
    public Cart setCartItemQuantity(String userId, String productId, int quantity) {
        return null;
    }

    @Override
    public Cart removeCartItemFromCart(String userId, String productId) {

        Cart cart = jpaCartRepository.getCurrentCartByUserId(userId);

        if(cart == null){
            throw new CartServiceException("No active Card found");
        }

        if (cart.getCartItems().stream().noneMatch(cartItem -> cartItem.getProductId().equals(productId))) {
            throw new CartServiceException("Product not in cart");
        }

        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getProductId().equals(productId)).findFirst().get();

        cart = jpaCartRepository.removeCartItemFromCart(String.valueOf(userId), cartItem);

        return cart;


    }

    @Override
    public Cart clearCart(String userId) {

        Cart cart = jpaCartRepository.getCurrentCartByUserId(userId);

        if(cart == null){
            throw new CartServiceException("No active Card found");
        }

        cart = jpaCartRepository.clearCart(userId);

        return cart;


    }

    @Override
    public Cart checkout(String userId) {

        Cart cart = jpaCartRepository.getCurrentCartByUserId(userId);

        Order order = new Order();
        order.setUserId(userId);
        order.setCart(cart);
        order.setStatus(OrderStatus.WAITING_FOR_PAYMENT);

        // set order Position
        List<OrderPosition> orderPositions = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {

            //TODO: Get product price & name from external service

            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setProductId(cartItem.getProductId());
            orderPosition.setQuantity(cartItem.getQuantity());

            //TODO: set actual values
            orderPosition.setPriceInEuroCents(BigDecimal.valueOf(100));
            orderPosition.setProductName(">>generic product<<");

            orderPositions.add(orderPosition);
        }

        // set order position
        orderPositions.sort((o1, o2) -> o1.getProductName().compareTo(o2.getProductName()));
        for (OrderPosition orderPosition : orderPositions) {
            orderPosition.setOrderPosition(orderPositions.indexOf(orderPosition));
        }

        order.setOrderPosition(orderPositions);

        try {
            order = jpaOrderRepository.save(order);
            cart = jpaCartRepository.changeStatus(cart.getId(), CartStatus.CHECKED_OUT);
            cart = jpaCartRepository.setOrder(cart.getId(), order);
        }
        catch (Exception e) {
            throw new CartServiceException("Order not saved");
        }

        return cart;
    }
}
