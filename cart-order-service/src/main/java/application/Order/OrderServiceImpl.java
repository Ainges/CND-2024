package application.Order;

import domain.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService{
    @Override
    public List<Order> getAllOrders() {


        List<Order> orders = new ArrayList<>();
        try {
            orders = new ArrayList<>();
        }
        catch (Exception e) {
            throw new OrderServiceException("Orders not found");
        }

        return orders;
    }

    @Override
    public Order getOrderById(long id) {
        return null;
    }

    @Override
    public Order changeOrderStatus(long id, String status) {
        return null;
    }

    @Override
    public List<Order> getOrdersOfUser(String userId) {
        return List.of();
    }
}
