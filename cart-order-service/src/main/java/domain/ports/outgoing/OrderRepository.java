package domain.ports.outgoing;

import adapter.jpa.entities.OrderEntity;

public interface OrderRepository {

    public OrderEntity getOrderById(long id);
    public OrderEntity addOrderItemToOrder(long userId, long productId);

}
