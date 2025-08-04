package dev.scastillo.ecommerce.order.domain.repository;

import dev.scastillo.ecommerce.order.domain.model.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    OrderItem createOrderItem(OrderItem orderItem);
}
