package dev.scastillo.ecommerce.unit.order.infrastructure.repository;

import dev.scastillo.ecommerce.order.domain.model.Order;
import dev.scastillo.ecommerce.order.domain.model.TopCustomerPurchasesResponse;
import dev.scastillo.ecommerce.order.infrastructure.repository.JpaOrderRepository;
import dev.scastillo.ecommerce.order.infrastructure.repository.SpringDataOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class JpaOrderRepositoryTest {
    private SpringDataOrderRepository springDataOrderRepository;
    private JpaOrderRepository jpaOrderRepository;

    @BeforeEach
    void setUp() {
        springDataOrderRepository = mock(SpringDataOrderRepository.class);
        jpaOrderRepository = new JpaOrderRepository(springDataOrderRepository);
    }
    @Test
    void findAllOrders_returnsAllOrders() {
        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);
        List<Order> orders = Arrays.asList(order1, order2);

        when(springDataOrderRepository.findAll()).thenReturn(orders);

        List<Order> result = jpaOrderRepository.findAllOrders();

        assertEquals(orders, result);
        verify(springDataOrderRepository, times(1)).findAll();
    }

    @Test
    void save_savesOrder() {
        Order order = mock(Order.class);
        when(springDataOrderRepository.save(order)).thenReturn(order);

        Order result = jpaOrderRepository.save(order);

        assertEquals(order, result);
        verify(springDataOrderRepository, times(1)).save(order);
    }

    @Test
    void findOrderById_returnsOrderOptional() {
        BigInteger orderId = BigInteger.valueOf(1);
        Order order = mock(Order.class);
        when(springDataOrderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> result = jpaOrderRepository.findOrderById(orderId);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
        verify(springDataOrderRepository, times(1)).findById(orderId);
    }

    @Test
    void findTopCustomersByOrders_returnsDtos() {
        int limit = 2;
        UUID uuid = UUID.randomUUID();
        Object[] row = new Object[]{uuid, "Cliente", 5L};
        List<Object[]> results = Collections.singletonList(row);

        when(springDataOrderRepository.findTopCustomersByPurchases(limit)).thenReturn(results);

        List<TopCustomerPurchasesResponse> dtos = jpaOrderRepository.findTopCustomersByOrders(limit);

        assertEquals(1, dtos.size());
        assertEquals(uuid, dtos.get(0).getCustomerId());
        verify(springDataOrderRepository, times(1)).findTopCustomersByPurchases(limit);
    }

}
