package adapter.jpa.repositories;



import domain.model.Cart;
import domain.model.CartItem;
import domain.model.CartStatus;
import domain.ports.outgoing.CartRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class JpaCartRepositoryTest {

    @Inject
    CartRepository cartRepository;

    @Test
    @TestTransaction
    void testGetAllCarts() {

        //Arrage
        Cart cart1 = new Cart();
        cart1.setUserId("firstUser");
        cart1.setStatus(CartStatus.OPEN);

        Cart cart2 = new Cart();
        cart2.setUserId("secondUser");
        cart2.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart1);
        cartRepository.save(cart2);
        List<Cart> carts = cartRepository.getAllCarts();

        //Assert
        assertEquals(2, carts.size());
    }

    @Test
    @TestTransaction
    void testGetCartById() {

        //Arrage
        Cart cart = new Cart();
        cart.setUserId("firstUser");
        cart.setStatus(CartStatus.OPEN);

        //Act
        cart = cartRepository.save(cart);
        Cart cartById = cartRepository.getCartById(cart.getId());

        //Assert
        assertNotNull(cartById);

    }

    @Test
    @TestTransaction
    void testGetCurrentCartByUserId() {

        //Arrage
        Cart cart = new Cart();
        cart.setUserId("firstUser");
        cart.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart);
        Cart currentCartByUserId = cartRepository.getCurrentCartByUserId("firstUser");
        Cart saftyCheckCart = cartRepository.getCurrentCartByUserId("firstUser");

        //Assert
        assertNotNull(currentCartByUserId);
        assertNotNull(saftyCheckCart);
    }

    @Test
    @TestTransaction
    void testGetCartsByUserId() {

        //Arrage
        Cart cart1 = new Cart();
        cart1.setUserId("firstUser");
        cart1.setStatus(CartStatus.OPEN);

        Cart cart2 = new Cart();
        cart2.setUserId("firstUser");
        cart2.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart1);
        cartRepository.save(cart2);
        List<Cart> carts = cartRepository.getCartsByUserId("firstUser");

        //Assert
        assertEquals(2, carts.size());
    }

    @Test
    @TestTransaction
    void testAddCartItemToCart() {

        //Arrage
        Cart cart = new Cart();
        cart.setUserId("firstUser");
        cart.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart);
        String productId = "1";
        int quantity = 1;
        CartItem cartItem = new CartItem(productId, quantity);

        Cart cartItemToCart = cartRepository.addCartItemToCart("firstUser", cartItem);
        Cart saftyCheckCart = cartRepository.getCurrentCartByUserId("firstUser");

        //Assert
        assertFalse(cartItemToCart.getCartItems().isEmpty());
        assertFalse(saftyCheckCart.getCartItems().isEmpty());
    }

    @Test
    @TestTransaction
    void testAddMultipleCartItemsToCart() {

        //Arrage
        Cart cart = new Cart();
        cart.setUserId("firstUser");
        cart.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart);
        String productId1 = "1";
        int quantity1 = 1;
        CartItem cartItem1 = new CartItem(productId1, quantity1);
        cartRepository.addCartItemToCart("firstUser", cartItem1);

        String productId2 = "2";
        int quantity2 = 2;
        CartItem cartItem2 = new CartItem(productId2, quantity2);

        Cart cartItemToCart = cartRepository.addCartItemToCart("firstUser", cartItem2);
        Cart saftyCheckCart = cartRepository.getCurrentCartByUserId("firstUser");

        //Assert
        assert (cartItemToCart.getCartItems().size() == 2);
        assert (saftyCheckCart.getCartItems().size() == 2);
    }

    @Test
    @TestTransaction
    void testRemoveCartItemFromCart() {

        //Arrage
        Cart cart = new Cart();
        cart.setUserId("firstUser");
        cart.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart);
        String productId = "1";
        int quantity = 1;
        CartItem cartItem = new CartItem(productId, quantity);
        cartRepository.addCartItemToCart("firstUser", cartItem);

        Cart cartItemToCart = cartRepository.removeCartItemFromCart("firstUser", cartItem);
        Cart saftyCheckCart = cartRepository.getCurrentCartByUserId("firstUser");

        //Assert
        assertTrue(cartItemToCart.getCartItems().isEmpty());
        assertTrue(saftyCheckCart.getCartItems().isEmpty());
    }

    @Test
    @TestTransaction
    void testClearCart() {

        //Arrage
        Cart cart = new Cart();
        cart.setUserId("firstUser");
        cart.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart);
        String productId = "1";
        int quantity = 1;
        CartItem cartItem = new CartItem(productId, quantity);
        cartRepository.addCartItemToCart("firstUser", cartItem);

        Cart cartItemToCart = cartRepository.clearCart("firstUser");
        Cart saftyCheckCart = cartRepository.getCurrentCartByUserId("firstUser");

        //Assert
        assertTrue(cartItemToCart.getCartItems().isEmpty());
        assertTrue(saftyCheckCart.getCartItems().isEmpty());
    }

    @Test
    @TestTransaction
    void testincreaseCartItemQuantity() {

        //Arrage
        Cart cart = new Cart();
        cart.setUserId("firstUser");
        cart.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart);
        String productId = "1";
        int quantity = 1;
        CartItem cartItem = new CartItem(productId, quantity);
        Cart returnedCart = cartRepository.addCartItemToCart("firstUser", cartItem);

        CartItem cartItemToCart = cartRepository.increaseCartItemQuantity(returnedCart.getCartItems().getFirst().getId(), 1);
        Cart saftyCheckCart = cartRepository.getCurrentCartByUserId("firstUser");

        //Assert
        assertEquals(2, cartItemToCart.getQuantity());
        assertEquals(2, saftyCheckCart.getCartItems().get(0).getQuantity());
    }

    @Test
    @TestTransaction
    void testDecreaseCartItemQuantity() {

        //Arrage
        Cart cart = new Cart();
        cart.setUserId("firstUser");
        cart.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart);
        String productId = "1";
        int quantity = 2;
        CartItem cartItem = new CartItem(productId, quantity);
        Cart returnedCart = cartRepository.addCartItemToCart("firstUser", cartItem);

        CartItem cartItemToCart = cartRepository.decreaseCartItemQuantity(returnedCart.getCartItems().getFirst().getId(), 1);
        Cart saftyCheckCart = cartRepository.getCurrentCartByUserId("firstUser");

        //Assert
        assertEquals(1, cartItemToCart.getQuantity());
        assertEquals(1, saftyCheckCart.getCartItems().get(0).getQuantity());
    }

    @Test
    @TestTransaction
    void testsetCartItemQuantity() {

        //Arrage
        Cart cart = new Cart();
        cart.setUserId("firstUser");
        cart.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart);
        String productId = "1";
        int quantity = 200;
        CartItem cartItem = new CartItem(productId, quantity);
        Cart returnedCart = cartRepository.addCartItemToCart("firstUser", cartItem);

        CartItem cartItemToCart = cartRepository.setCartItemQuantity(returnedCart.getCartItems().getFirst().getId(), 5);
        Cart saftyCheckCart = cartRepository.getCurrentCartByUserId("firstUser");

        //Assert
        assertEquals(5, cartItemToCart.getQuantity());
        assertEquals(5, saftyCheckCart.getCartItems().get(0).getQuantity());
    }

    @Test
    @TestTransaction
    void testgetAllCartItemsOfCart() {

        //Arrage
        Cart cart = new Cart();
        cart.setUserId("firstUser");
        cart.setStatus(CartStatus.OPEN);

        //Act
        cartRepository.save(cart);
        String productId1 = "1";
        int quantity1 = 1;
        CartItem cartItem1 = new CartItem(productId1, quantity1);
        cartRepository.addCartItemToCart("firstUser", cartItem1);

        String productId2 = "2";
        int quantity2 = 2;
        CartItem cartItem2 = new CartItem(productId2, quantity2);
        cart = cartRepository.addCartItemToCart("firstUser", cartItem2);

        List<CartItem> cartItems = cartRepository.getAllCartItemsOfCart(cart.getId());

        //Assert
        assertEquals(2, cartItems.size());
    }

    //TODO: Add test for CheckoutCart
}
