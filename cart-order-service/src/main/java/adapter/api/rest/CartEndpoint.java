package adapter.api.rest;

import adapter.api.rest.dto.CartItemDto;
import adapter.jpa.entities.CartEntity;
import application.Cart.CartServiceImpl;
import application.Cart.CartServiceException;
import domain.model.Cart;
import jakarta.inject.Inject;
import jakarta.persistence.Column;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/")
public class CartEndpoint {

    @Inject
    CartServiceImpl cartService;

    @ConfigProperty(name = "host")
    String host;

    @ConfigProperty(name = "port")
    String port;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CartEndpoint.class);

    @GET()
    @Path("carts")
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
    @Path("carts/userid/{userId}/current")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCurrentCartByUserIdEndpoint(@PathParam("userId") String userId) {

        Cart cart = new Cart();

        try {
            cart = cartService.getCartByUserId(userId);
        } catch (CartServiceException e) {

            if (e.getMessage().equals("User has no active cart")) {
                logger.error("### User with id {} has no active cart ###", userId);
                return Response
                        .status(jakarta.ws.rs.core.Response.Status.NOT_FOUND)
                        .entity(Map.of("message", "User with id " + userId + " has no active cart"))
                        .build();
            }

            return Response
                    .status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", e.getMessage()))
                    .build();
        }
        return Response
                .status(jakarta.ws.rs.core.Response.Status.OK)
                .entity(Map.of("cart", cart))
                .build();
    }



    @GET()
    @Path("carts/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartsByIdEndpoint(@PathParam("id") long id) {

        Cart cart = new Cart();

        try {
            cart = cartService.getCartById(id);
        }

        // specific exception handling
        catch (CartServiceException e) {

            if (e.getMessage().equals("Cart not found")) {

                logger.error("### Cart with id " + id + " not found ###");

                return Response
                        .status(jakarta.ws.rs.core.Response.Status.NOT_FOUND)
                        .entity(Map.of("message", "Cart with id " + id + " not found"))
                        .build();
            }

            return Response
                    .status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", e.getMessage()))
                    .build();

        // fallback exception handling
        } catch (Exception e) {

            logger.error("### Error while getting cart by id: ###", e);
            return Response
                    .status(jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("message", "An unknown error occurred"))
                    .build();
        }
        return Response
                .status(jakarta.ws.rs.core.Response.Status.OK)
                .entity(Map.of("cart", cart))
                .build();



    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("users/{userId}/cart/items/")
    public Response addCartItemToCartEndpoint(@PathParam("userId") String userId, CartItemDto cartItemDto) {

        Cart cart = new Cart();

        try {
            cart = cartService.addCartItemToCart(userId,
                    cartItemDto.getProductId(), cartItemDto.getQuantity());
        } catch (CartServiceException e) {
            return Response
                    .status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", e.getMessage()))
                    .build();
        }
        return Response
                .status(jakarta.ws.rs.core.Response.Status.OK)
                .entity(Map.of("cart", cart))
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("users/{userId}/checkout")
    public Response checkoutCartEndpoint(@PathParam("userId") String userId) {
        Cart cart = new Cart();

        try {
            cart = cartService.checkout(userId);
        } catch (CartServiceException e) {
            return Response
                    .status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", e.getMessage()))
                    .build();
        }
        return Response
                .status(jakarta.ws.rs.core.Response.Status.OK)
                .entity(Map.of("cart", cart))
                .build();
    }

}
