package application.Order;

import domain.model.Order;
import domain.model.OrderStatus;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();
    Order getOrderById(long id);
    Order changeOrderStatus(long id, OrderStatus orderStatus);
    List<Order> getOrdersOfUser(String userId);


}
