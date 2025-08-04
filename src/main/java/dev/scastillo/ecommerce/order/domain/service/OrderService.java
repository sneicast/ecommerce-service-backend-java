package dev.scastillo.ecommerce.order.domain.service;

import dev.scastillo.ecommerce.order.domain.model.Order;
import dev.scastillo.ecommerce.order.domain.model.TopCustomerPurchasesResponse;
import dev.scastillo.ecommerce.order.domain.model.TopProductSalesResponse;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Order> findAllOrders();
    Order createOrder(Order order, UUID userId);
    Order findOrderById(BigInteger orderId);
    List<TopProductSalesResponse> findTopProductsBySales(int limit);
    List<TopCustomerPurchasesResponse> findTopCustomersByOrders(int limit);

}
