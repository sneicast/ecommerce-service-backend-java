package dev.scastillo.ecommerce.order.adapter.web.controller;

import dev.scastillo.ecommerce.order.adapter.web.dto.OrderCreateRequestDto;
import dev.scastillo.ecommerce.order.adapter.web.dto.OrderDto;
import dev.scastillo.ecommerce.order.adapter.web.mapper.OrderMapper;
import dev.scastillo.ecommerce.order.domain.service.OrderService;
import dev.scastillo.ecommerce.shared.auth.AuthenticatedUserId;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderCreateRequestDto requestDto, @AuthenticatedUserId UUID userId){
        return orderMapper.toDto(
                orderService.createOrder(orderMapper.toDomain(requestDto), userId)
        );
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.findAllOrders().stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable("id") BigInteger orderId) {
        return orderMapper.toDto(orderService.findOrderById(orderId));
    }
}
