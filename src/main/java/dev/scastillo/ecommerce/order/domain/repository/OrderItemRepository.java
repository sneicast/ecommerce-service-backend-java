package dev.scastillo.ecommerce.order.domain.repository;

import dev.scastillo.ecommerce.order.domain.model.OrderItem;
import dev.scastillo.ecommerce.order.domain.model.TopProductSalesResponse;

import java.util.List;

public interface OrderItemRepository {
    OrderItem createOrderItem(OrderItem orderItem);
    List<TopProductSalesResponse> findTopProductsBySales(int limit);

}
