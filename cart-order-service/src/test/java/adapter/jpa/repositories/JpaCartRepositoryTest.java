package adapter.jpa.repositories;



import domain.model.Cart;
import domain.model.CartStatus;
import domain.ports.outgoing.CartRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class JpaCartRepositoryTest {

//    @Inject
//    CartRepository cartRepository;
//
//    @Test
//    public void testGetAllCarts() {
//
//        //Arrage
//        Cart cart = new Cart();
//        cart.setStatus(CartStatus.OPEN);
//
//        //Act
//        cartRepository.save(cart);
//        List<Cart> carts = cartRepository.getAllCarts();
//
//        //Assert
//        assert(!carts.isEmpty());
//    }


    @Test
    public void testTest(){

        given()
          .when().get("/cart")
          .then()
             .statusCode(200);
    }
}
