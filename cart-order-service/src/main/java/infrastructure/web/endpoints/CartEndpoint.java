package infrastructure.web.endpoints;

import application.Cart.CartServiceImpl;
import application.Cart.CartServiceException;
import domain.Cart;
import infrastructure.web.dto.CartCreateDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
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
    public Response getCartsEndpoint() {

        List<Cart> cartList = new ArrayList<>();

        try {
            cartList = cartService.getAllCarts();
        } catch (CartServiceException e) {
            return Response
                    .status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", e.getMessage()))
                    .build();
        }
        return Response
                .status(jakarta.ws.rs.core.Response.Status.OK)
                .entity(Map.of("carts", cartList))
                .build();
    }



    @GET()
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartsByIdEndpoint(long id) {

        Cart cart = new Cart();
        try {
            cart = cartService.getCartById(id);
        }
        catch (CartServiceException e) {
            return Response
                    .status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", e.getMessage()))
                    .build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("cart", cart);

        return Response
                .status(jakarta.ws.rs.core.Response.Status.OK)
                .entity(response)
                .build();



    }

/*    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCartEndpoint(CartCreateDto cartCreateDto) {

        Cart cart = new Cart();
        try {
            cart = cartService.addCart(cartCreateDto);
        }
        catch (CartServiceException e) {

            return Response
                    .status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", e.getMessage()))
                    .build();

        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cart created successfully");
        response.put("cartId", cart.getId());


        return Response
                .status(jakarta.ws.rs.core.Response.Status.CREATED)
                .header("Location", host + ":" + port + "/cart/" + cart.getId())
                .entity(response)
                .build();

    }*/

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCartEndpoint(long id) {

        Cart cart = new Cart();
        try {
            cart = cartService.deleteCart(id);
        }
        catch (CartServiceException e) {

            // TODO: Implement CartNotFoundException
            if(e.getMessage().contains("already deleted or does not exist")) {
                return Response
                        .status(Response.Status.NO_CONTENT)
                        .entity(Map.of("message", e.getMessage()))
                        .build();
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", e.getMessage()))
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(Map.of("message", "Cart deleted successfully", "cart", cart))
                .build();

    }

    // TODO: Implement addProductToCartEndpoint



}
