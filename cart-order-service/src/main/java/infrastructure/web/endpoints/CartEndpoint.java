package infrastructure.web.endpoints;

import application.CartServiceImpl;
import domain.Cart;
import infrastructure.web.dto.CartCreateDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/cart")
public class CartEndpoint {

    @Inject
    CartServiceImpl cartService;

    @ConfigProperty(name = "host")
    String host;

    @ConfigProperty(name = "port")
    String port;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cart> getCartsEndpoint() {
        return cartService.getAllCarts();
    }

    @GET()
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cart getCartsByIdEndpoint(long id) {
        return cartService.getCartById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCartEndpoint(CartCreateDto cartCreateDto) {

        Cart cart = cartService.addCart(cartCreateDto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cart created successfully");
        response.put("cartId", cart.getId());


        return Response
                .status(jakarta.ws.rs.core.Response.Status.CREATED)
                .header("Location", host + ":" + port + "/cart/" + cart.getId())
                .entity(response)
                .build();

    }


}
