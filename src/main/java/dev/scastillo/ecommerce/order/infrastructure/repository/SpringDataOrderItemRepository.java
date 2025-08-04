package dev.scastillo.ecommerce.order.infrastructure.repository;

import dev.scastillo.ecommerce.order.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.math.BigInteger;
import java.util.List;

public interface SpringDataOrderItemRepository extends JpaRepository<OrderItem, BigInteger> {
    @Query(
            value = "SELECT oi.product_id AS productId, oi.product_name AS productName, SUM(oi.quantity) AS totalSales " +
                    "FROM order_items oi GROUP BY oi.product_id, oi.product_name " +
                    "ORDER BY totalSales DESC LIMIT :limit",
            nativeQuery = true)
    List<Object[]> findTopProductSales(int limit);

}
