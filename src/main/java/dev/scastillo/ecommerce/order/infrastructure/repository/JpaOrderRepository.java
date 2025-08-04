package dev.scastillo.ecommerce.order.infrastructure.repository;

import dev.scastillo.ecommerce.order.domain.model.Order;
import dev.scastillo.ecommerce.order.domain.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JpaOrderRepository implements OrderRepository {
    private final SpringDataOrderRepository repository;
    @Override
    public List<Order> findAllOrders() {
        return repository.findAll();
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public Optional<Order> findOrderById(BigInteger orderId) {
        return repository.findById(orderId);
    }
}
