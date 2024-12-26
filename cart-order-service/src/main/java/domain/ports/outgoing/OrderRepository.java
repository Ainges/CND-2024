package domain.ports.outgoing;


import domain.model.Order;
import domain.model.OrderStatus;

import java.util.List;

public interface OrderRepository {

    public List<Order> getAllOrders();
    public Order getOrderById(long id);
    public Order updateStatus(long id, OrderStatus status);
    public List<Order> getOrderByUserId(String userId);
    public Order getOrderByCartId(long cartId);
    public Order save(Order order);



}
