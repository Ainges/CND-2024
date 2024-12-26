package application.Order;

import domain.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();
    Order getOrderById(long id);
    Order changeOrderStatus(long id, String status);
    List<Order> getOrdersOfUser(String userId);


}
