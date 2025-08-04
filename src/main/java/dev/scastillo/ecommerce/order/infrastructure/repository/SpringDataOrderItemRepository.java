package dev.scastillo.ecommerce.order.infrastructure.repository;

import dev.scastillo.ecommerce.order.domain.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface SpringDataOrderItemRepository extends JpaRepository<OrderItem, BigInteger> {

}
