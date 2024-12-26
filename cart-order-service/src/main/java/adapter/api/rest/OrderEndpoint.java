package adapter.api.rest;

import adapter.jpa.repositories.JpaOrderRepository;
import application.Order.OrderServiceException;
import application.Order.OrderServiceImpl;
import domain.model.Order;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path(("orders"))
public class OrderEndpoint {


    @Inject
    OrderServiceImpl orderService;

    @GET
    @Produces("application/json")
    public Response getAllOrders() {
        List<Order> orderList = new ArrayList<>();

        try {
            orderList = orderService.getAllOrders();
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

    @GET
    @Path("user/{userId}")
    @Produces("application/json")
    public Response getOrdersOfUser(@PathParam("userId") String userId) {
        List<Order> orderList = new ArrayList<>();

        try {
            orderList = orderService.getOrdersOfUser(userId);
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

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getOrderById(@PathParam("id") long id) {
        Order order = new Order();

        try {
            order = orderService.getOrderById(id);
        } catch (OrderServiceException e) {
            return Response
                    .status(jakarta.ws.rs.core.Response.Status.BAD_REQUEST)
                    .entity(Map.of("message", e.getMessage()))
                    .build();
        }
        return Response
                .status(jakarta.ws.rs.core.Response.Status.OK)
                .entity(Map.of("order", order))
                .build();
    }


}
