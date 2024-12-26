package adapter.jpa.repositories;

import adapter.jpa.entities.CartEntity;
import adapter.jpa.entities.CartItemEntity;
import adapter.jpa.entities.OrderEntity;
import adapter.jpa.entities.OrderPositionEntity;
import domain.model.*;
import domain.ports.outgoing.OrderRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class JpaOrderRepository implements OrderRepository, PanacheRepository<OrderEntity> {


    private static final Logger logger = LoggerFactory.getLogger(JpaOrderRepository.class);


    @Override
    public List<Order> getAllOrders() {
        List<OrderEntity> orderEntityList = listAll();
        List<Order> orders = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntityList) {
            orders.add(orderEntity.toOrder());
        }
        return orders;

    }

    @Override
    public Order getOrderById(long id) {
        Order order = null;
        try {
             order = find("id", id).firstResult().toOrder();
        }
        catch (Exception e) {
            throw new JpaOrderRepositoryException("Order not found");
        }
        return order;
    }

    @Override
    @Transactional
    public Order save(Order order) {

        OrderEntity orderEntity = new OrderEntity();
        Cart cart = order.getCart();
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(cart.getId());
        cartEntity.setUserId(cart.getUserId());
        cartEntity.setStatus(cart.getStatus());

        List<CartItemEntity> cartItemEntityList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            CartItemEntity cartItemEntity = new CartItemEntity();
            cartItemEntity.setId(cartItem.getId());
            cartItemEntity.setProductId(cartItem.getProductId());
            cartItemEntity.setQuantity(cartItem.getQuantity());
            cartItemEntityList.add(cartItemEntity);

        }
        cartEntity.setCartItems(cartItemEntityList);

        orderEntity.setCart(cartEntity);
        orderEntity.setStatus(order.getStatus());
        orderEntity.setUserId(order.getUserId());

        List<OrderPositionEntity> orderPositions = new ArrayList<>();
        for (OrderPosition orderPosition : order.getOrderPosition()) {
            OrderPositionEntity orderPositionEntity = new OrderPositionEntity();
            orderPositionEntity.setId(orderPosition.getId());
            orderPositionEntity.setProductId(orderPosition.getProductId());
            orderPositionEntity.setQuantity(orderPosition.getQuantity());
            orderPositionEntity.setPriceInEuroCents(orderPosition.getPriceInEuroCents());
            orderPositionEntity.setOrderEntity(orderEntity);

            orderPositions.add(orderPositionEntity);
        }

        orderEntity.setOrderPositionEntities(orderPositions);

        try{
            persist(orderEntity);
        }
        catch (Exception e) {
            throw new JpaOrderRepositoryException("Order not saved");
        }

        return orderEntity.toOrder();
    }

    @Override
    @Transactional
    public Order updateStatus(long id, OrderStatus status) {
        OrderEntity orderEntity = find("id", id).firstResult();
        orderEntity.setStatus(status);
        return orderEntity.toOrder();
    }

    @Override
    public List<Order> getOrderByUserId(String userId) {
        List<OrderEntity> orderEntities = find("userId", userId).list();

        List<Order> orders = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntities) {
            orders.add(orderEntity.toOrder());
        }
        return orders;
    }

    @Override
    public Order getOrderByCartId(long cartId) {
        return null;
    }
}
