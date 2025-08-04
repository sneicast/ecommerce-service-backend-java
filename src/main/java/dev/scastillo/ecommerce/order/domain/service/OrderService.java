package dev.scastillo.ecommerce.order.domain.service;

import dev.scastillo.ecommerce.order.domain.model.Order;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Order> findAllOrders();
    Order createOrder(Order order, UUID userId);
    Order findOrderById(BigInteger orderId);

}
