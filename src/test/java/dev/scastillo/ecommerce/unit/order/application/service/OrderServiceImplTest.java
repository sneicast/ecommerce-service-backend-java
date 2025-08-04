package dev.scastillo.ecommerce.unit.order.application.service;

import dev.scastillo.ecommerce.customer.domain.model.Customer;
import dev.scastillo.ecommerce.customer.domain.service.CustomerService;
import dev.scastillo.ecommerce.order.application.service.OrderServiceImpl;
import dev.scastillo.ecommerce.order.domain.model.Order;
import dev.scastillo.ecommerce.order.domain.model.OrderItem;
import dev.scastillo.ecommerce.order.domain.model.TopCustomerPurchasesResponse;
import dev.scastillo.ecommerce.order.domain.model.TopProductSalesResponse;
import dev.scastillo.ecommerce.order.domain.repository.OrderItemRepository;
import dev.scastillo.ecommerce.order.domain.repository.OrderRepository;
import dev.scastillo.ecommerce.product.domain.model.Product;
import dev.scastillo.ecommerce.product.domain.model.ProductStock;
import dev.scastillo.ecommerce.product.domain.repository.ProductStockRepository;
import dev.scastillo.ecommerce.product.domain.service.ProductService;
import dev.scastillo.ecommerce.promotion.domain.model.Promotion;
import dev.scastillo.ecommerce.promotion.domain.repository.PromotionRepository;
import dev.scastillo.ecommerce.shared.exception.NotFoundException;
import dev.scastillo.ecommerce.user.domain.model.User;
import dev.scastillo.ecommerce.user.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class OrderServiceImplTest {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private CustomerService customerService;
    private UserService userService;
    private ProductService productService;
    private ProductStockRepository productStockRepository;
    private PromotionRepository promotionRepository;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderItemRepository = mock(OrderItemRepository.class);
        customerService = mock(CustomerService.class);
        userService = mock(UserService.class);
        productService = mock(ProductService.class);
        productStockRepository = mock(ProductStockRepository.class);
        promotionRepository = mock(PromotionRepository.class);

        orderService = new OrderServiceImpl(
                orderRepository,
                orderItemRepository,
                customerService,
                userService,
                productService,
                productStockRepository,
                promotionRepository
        );
    }

    @Test
    void findAllOrders_returnsAllOrders() {
        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAllOrders()).thenReturn(orders);

        List<Order> result = orderService.findAllOrders();

        assertEquals(orders, result);
        verify(orderRepository, times(1)).findAllOrders();
    }

    @Test
    void findOrderById_returnsOrder_whenOrderExists() {
        BigInteger orderId = BigInteger.ONE;
        Order order = mock(Order.class);
        when(orderRepository.findOrderById(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.findOrderById(orderId);

        assertEquals(order, result);
        verify(orderRepository, times(1)).findOrderById(orderId);
    }

    @Test
    void findOrderById_throwsException_whenOrderDoesNotExist() {
        BigInteger orderId = BigInteger.ONE;
        when(orderRepository.findOrderById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.findOrderById(orderId));
        verify(orderRepository, times(1)).findOrderById(orderId);
    }

    @Test
    void findTopProductsBySales_returnsList() {
        int limit = 5;
        List<TopProductSalesResponse> topProducts = Collections.emptyList();
        when(orderItemRepository.findTopProductsBySales(limit)).thenReturn(topProducts);

        List<TopProductSalesResponse> result = orderService.findTopProductsBySales(limit);

        assertEquals(topProducts, result);
        verify(orderItemRepository, times(1)).findTopProductsBySales(limit);
    }

    @Test
    void findTopCustomersByOrders_returnsList() {
        int limit = 5;
        List<TopCustomerPurchasesResponse> topCustomers = Collections.emptyList();
        when(orderRepository.findTopCustomersByOrders(limit)).thenReturn(topCustomers);

        List<TopCustomerPurchasesResponse> result = orderService.findTopCustomersByOrders(limit);

        assertEquals(topCustomers, result);
        verify(orderRepository, times(1)).findTopCustomersByOrders(limit);
    }
}
