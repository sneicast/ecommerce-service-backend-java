package dev.scastillo.ecommerce.order.infrastructure.repository;

import dev.scastillo.ecommerce.order.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface SpringDataOrderRepository extends JpaRepository<Order, BigInteger> {
}
