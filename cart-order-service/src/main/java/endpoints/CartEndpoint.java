package endpoints;

import dto.cart.CartCreateDto;
import entity.Cart;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import repository.CartRepository;

import java.util.List;


@Path("/cart")
public class CartEndpoint {

    @Inject
    CartRepository cartRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cart> getCartsEndpoint() {
        return cartRepository.getAllCarts();
    }

    @GET()
    @Path("/{id}")
    public String getCartsByIdEndpoint(long id) {
        return cartRepository.getCartById(id).toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void addCartEndpoint(CartCreateDto cartCreateDto) {
        cartRepository.addCart(cartCreateDto);
    }


}
