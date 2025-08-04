package dev.scastillo.ecommerce.order.infrastructure.repository;

import dev.scastillo.ecommerce.order.domain.model.OrderItem;
import dev.scastillo.ecommerce.order.domain.model.TopProductSalesResponse;
import dev.scastillo.ecommerce.order.domain.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class JpaOrderItemRepository implements OrderItemRepository {
    private final SpringDataOrderItemRepository repository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public List<TopProductSalesResponse> findTopProductsBySales(int limit) {
        List<Object[]> results = repository.findTopProductSales(limit);
        List<TopProductSalesResponse> dtos = results.stream()
                .map(r -> new TopProductSalesResponse(
                        ((Number) r[0]).longValue(),
                        (String) r[1],
                        ((Number) r[2]).longValue()))
                .collect(Collectors.toList());
        return dtos;
    }
}
