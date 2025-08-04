package dev.scastillo.ecommerce.order.infrastructure.repository;

import dev.scastillo.ecommerce.order.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface SpringDataOrderRepository extends JpaRepository<Order, BigInteger> {
    @Query(
            value = "SELECT o.customer_id AS customerId, (c.first_name || ' ' || c.last_name) AS fullName, COUNT(o.id) AS totalPurchases " +
                    "FROM orders o " +
                    "JOIN customers c ON o.customer_id = c.id " +
                    "GROUP BY o.customer_id, c.first_name, c.last_name " +
                    "ORDER BY totalPurchases DESC LIMIT :limit",
            nativeQuery = true)
    List<Object[]> findTopCustomersByPurchases(int limit);
}
