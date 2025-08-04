package dev.scastillo.ecommerce.order.infrastructure.repository;

import dev.scastillo.ecommerce.order.domain.model.Order;
import dev.scastillo.ecommerce.order.domain.model.TopCustomerPurchasesResponse;
import dev.scastillo.ecommerce.order.domain.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public List<TopCustomerPurchasesResponse> findTopCustomersByOrders(int limit) {
        List<Object[]> results = repository.findTopCustomersByPurchases(limit);
        List<TopCustomerPurchasesResponse> dtos = results.stream()
                .map(r -> new TopCustomerPurchasesResponse(
                        ((UUID) r[0]),
                        (String) r[1],
                        ((Number) r[2]).longValue()))
                .collect(Collectors.toList());
        return dtos;

    }
}
