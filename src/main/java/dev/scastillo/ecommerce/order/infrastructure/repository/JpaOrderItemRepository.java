package dev.scastillo.ecommerce.order.infrastructure.repository;

import dev.scastillo.ecommerce.order.domain.model.OrderItem;
import dev.scastillo.ecommerce.order.domain.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class JpaOrderItemRepository implements OrderItemRepository {
    private final SpringDataOrderItemRepository repository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return repository.save(orderItem);
    }
}
