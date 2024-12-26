package adapter.api.rest;

import adapter.jpa.repositories.JpaOrderRepository;
import application.Cart.CartServiceException;
import application.Order.OrderServiceException;
import domain.model.Cart;
import domain.model.Order;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path(("orders"))
public class OrderEndpoint {


    @Inject
    JpaOrderRepository jpaOrderRepository;

    @GET
    @Produces("application/json")
    public Response getAllOrders() {
        List<Order> orderList = new ArrayList<>();

        try {
            orderList = jpaOrderRepository.getAllOrders();
        } catch (OrderServiceException e) {
            return Response
                    .status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", e.getMessage()))
                    .build();
        }
        return Response
                .status(jakarta.ws.rs.core.Response.Status.OK)
                .entity(Map.of("orders", orderList))
                .build();
    }
}
