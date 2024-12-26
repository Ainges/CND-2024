package application.Order;

import adapter.jpa.repositories.JpaOrderRepository;
import domain.model.Order;
import domain.model.OrderStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderServiceImpl implements OrderService{

    @Inject
    JpaOrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {


        List<Order> orders = new ArrayList<>();
        try {
            orders = orderRepository.getAllOrders();
        }
        catch (Exception e) {
            throw new OrderServiceException("Orders not found");
        }

        return orders;
    }

    @Override
    public Order getOrderById(long orderId) {

        Order order = new Order();
        try {
            order = orderRepository.getOrderById(orderId);
        }
        catch (Exception e) {
            throw new OrderServiceException("Order not found");
        }
        return order;

    }

    @Override
    public Order changeOrderStatus(long orderId, OrderStatus orderStatus) {
        Order order = new Order();
        try {
            order = orderRepository.updateStatus(orderId, orderStatus);
        }
        catch (Exception e) {
            throw new OrderServiceException("Order not found");
        }
        return order;
    }

    @Override
    public List<Order> getOrdersOfUser(String userId) {

        List<Order> orders = new ArrayList<>();
        try {
            orders = orderRepository.getOrdersByUserId(userId);
        }
        catch (Exception e) {
            throw new OrderServiceException("Orders not found");
        }

        return orders;

    }
}
