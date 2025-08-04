package dev.scastillo.ecommerce.order.domain.repository;

import dev.scastillo.ecommerce.order.domain.model.Order;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findAllOrders();
    Order save(Order order);
    Optional<Order> findOrderById(BigInteger orderId);
}
