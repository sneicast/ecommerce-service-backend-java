package dev.scastillo.ecommerce.unit.order.adapter.web.controller;

import dev.scastillo.ecommerce.order.adapter.web.controller.OrderController;
import dev.scastillo.ecommerce.order.adapter.web.dto.OrderCreateRequestDto;
import dev.scastillo.ecommerce.order.adapter.web.dto.OrderDto;
import dev.scastillo.ecommerce.order.adapter.web.mapper.OrderMapper;
import dev.scastillo.ecommerce.order.domain.model.Order;
import dev.scastillo.ecommerce.order.domain.service.OrderService;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class OrderControllerTest {
    private final OrderService orderService = mock(OrderService.class);
    private final OrderMapper orderMapper = mock(OrderMapper.class);
    private final OrderController controller = new OrderController(orderService, orderMapper);

    @Test
    void createOrder_returnsOrderDto() {
        OrderCreateRequestDto requestDto = mock(OrderCreateRequestDto.class);
        UUID userId = UUID.randomUUID();
        Order order = mock(Order.class);
        OrderDto orderDto = mock(OrderDto.class);

        when(orderMapper.toDomain(requestDto)).thenReturn(order);
        when(orderService.createOrder(order, userId)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = controller.createOrder(requestDto, userId);

        assertEquals(orderDto, result);
        verify(orderMapper).toDomain(requestDto);
        verify(orderService).createOrder(order, userId);
        verify(orderMapper).toDto(order);
    }

    @Test
    void getAllOrders_returnsListOfOrderDto() {
        Order order = mock(Order.class);
        OrderDto orderDto = mock(OrderDto.class);
        List<Order> orders = List.of(order);

        when(orderService.findAllOrders()).thenReturn(orders);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        List<OrderDto> result = controller.getAllOrders();

        assertEquals(List.of(orderDto), result);
        verify(orderService).findAllOrders();
        verify(orderMapper).toDto(order);
    }

    @Test
    void getOrderById_returnsOrderDto() {
        BigInteger orderId = BigInteger.ONE;
        Order order = mock(Order.class);
        OrderDto orderDto = mock(OrderDto.class);

        when(orderService.findOrderById(orderId)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = controller.getOrderById(orderId);

        assertEquals(orderDto, result);
        verify(orderService).findOrderById(orderId);
        verify(orderMapper).toDto(order);
    }
}
