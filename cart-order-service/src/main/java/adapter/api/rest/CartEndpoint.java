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
    public Response getCartsByIdEndpoint(@PathParam("id") long id) {

        Cart cart = new Cart();

        try {
            cart = cartService.getCartById(id);
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

    @Path("user/{userId}/product/")
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




}
