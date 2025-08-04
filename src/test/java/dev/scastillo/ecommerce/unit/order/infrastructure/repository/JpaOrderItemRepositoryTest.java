package dev.scastillo.ecommerce.unit.order.infrastructure.repository;

import dev.scastillo.ecommerce.order.domain.model.OrderItem;
import dev.scastillo.ecommerce.order.domain.model.TopProductSalesResponse;
import dev.scastillo.ecommerce.order.infrastructure.repository.JpaOrderItemRepository;
import dev.scastillo.ecommerce.order.infrastructure.repository.SpringDataOrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class JpaOrderItemRepositoryTest {
    private SpringDataOrderItemRepository springDataOrderItemRepository;
    private JpaOrderItemRepository jpaOrderItemRepository;

    @BeforeEach
    void setUp() {
        springDataOrderItemRepository = mock(SpringDataOrderItemRepository.class);
        jpaOrderItemRepository = new JpaOrderItemRepository(springDataOrderItemRepository);
    }

    @Test
    void createOrderItem_savesOrderItem() {
        OrderItem orderItem = mock(OrderItem.class);
        when(springDataOrderItemRepository.save(orderItem)).thenReturn(orderItem);

        OrderItem result = jpaOrderItemRepository.createOrderItem(orderItem);

        assertEquals(orderItem, result);
        verify(springDataOrderItemRepository, times(1)).save(orderItem);
    }

    @Test
    void findTopProductsBySales_returnsDtos() {
        int limit = 2;
        Object[] row = new Object[]{1L, "Producto", 10L};
        List<Object[]> results = Collections.singletonList(row);

        when(springDataOrderItemRepository.findTopProductSales(limit)).thenReturn(results);

        List<TopProductSalesResponse> dtos = jpaOrderItemRepository.findTopProductsBySales(limit);

        assertEquals(1, dtos.size());
        assertEquals(1L, dtos.get(0).getProductId());
        assertEquals("Producto", dtos.get(0).getProductName());
        verify(springDataOrderItemRepository, times(1)).findTopProductSales(limit);
    }
}
