package dev.scastillo.ecommerce.order.application.service;

import dev.scastillo.ecommerce.customer.domain.model.Customer;
import dev.scastillo.ecommerce.customer.domain.service.CustomerService;
import dev.scastillo.ecommerce.order.domain.model.Order;
import dev.scastillo.ecommerce.order.domain.model.OrderItem;
import dev.scastillo.ecommerce.order.domain.model.TopCustomerPurchasesResponse;
import dev.scastillo.ecommerce.order.domain.model.TopProductSalesResponse;
import dev.scastillo.ecommerce.order.domain.repository.OrderItemRepository;
import dev.scastillo.ecommerce.order.domain.repository.OrderRepository;
import dev.scastillo.ecommerce.order.domain.service.OrderService;
import dev.scastillo.ecommerce.product.domain.model.Product;
import dev.scastillo.ecommerce.product.domain.repository.ProductStockRepository;
import dev.scastillo.ecommerce.product.domain.service.ProductService;
import dev.scastillo.ecommerce.promotion.adapter.web.dto.PromotionDto;
import dev.scastillo.ecommerce.promotion.domain.model.Promotion;
import dev.scastillo.ecommerce.promotion.domain.repository.PromotionRepository;
import dev.scastillo.ecommerce.shared.exception.NotFoundException;
import dev.scastillo.ecommerce.user.domain.model.User;
import dev.scastillo.ecommerce.user.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerService customerService;
    private final UserService userService;
    private final ProductService productService;
    private final ProductStockRepository productStockRepository;
    private final PromotionRepository promotionRepository;

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAllOrders();
    }

    @Override
    public Order createOrder(Order order, UUID userId) {
        //Obtener usuario que creo la compra
        User user = userService.getUserById(userId);
        order.setCreatedBy(user);
        order.setCreatedByName(user.getFirstName() + " " + user.getLastName());
        //obtener customer
        Customer customer = customerService.findCustomerById(order.getCustomer().getId());
        order.setCustomer(customer);

        //Obtener cada producto de los items del pedido
        List<OrderItem> orderItems = order.getItems();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for( OrderItem item : orderItems) {
            Product product = productService.getProductById(item.getProduct().getId());
            if (product.getStock().getQuantity() < item.getQuantity()) {
                throw new NotFoundException("No hay suficiente stock para el producto: " + product.getTitle());
            }
            item.setOrder(order);
            item.setProduct(product);
            item.setProductName(product.getTitle());
            item.setUnitPrice(product.getPrice());
            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        order.setItems(orderItems);
        order.setTotalAmount(totalPrice);

        decreaseStockForOrderItems(orderItems);
        //Aplicar promociones si existen
        Optional<Promotion> promotionOptional = promotionRepository.findActivePromotionByDate(LocalDate.now());
        if (promotionOptional.isPresent()) {
            Promotion promotion = promotionOptional.get();
            order.setPromotion(promotion);

            BigDecimal discountPercentage = promotion.getGlobalDiscountPercentage();
            BigDecimal discountAmount = order.getTotalAmount()
                    .multiply(discountPercentage)
                    .divide(BigDecimal.valueOf(100));
            order.setDiscountAmount(discountAmount);

            BigDecimal finalAmount = order.getTotalAmount().subtract(discountAmount);
            order.setFinalAmount(finalAmount);
        } else {
            order.setPromotion(null);
            order.setDiscountAmount(BigDecimal.ZERO);
            order.setFinalAmount(order.getTotalAmount());
        }
        Order savedOrder = orderRepository.save(order);

        Order detailedOrder = this.findOrderById(savedOrder.getId());

        return detailedOrder;
    }

    @Override
    public Order findOrderById(BigInteger orderId) {
        Optional<Order> orderOptional = orderRepository.findOrderById(orderId);
        if (orderOptional.isEmpty()) {
            throw new NotFoundException("No fuen encontrado el order con id: " + orderId);
        }
        return orderOptional.get();
       // return orderRepository.findOrderById(orderId);
    }

    @Override
    public List<TopProductSalesResponse> findTopProductsBySales(int limit) {
        return orderItemRepository.findTopProductsBySales(limit);
    }

    @Override
    public List<TopCustomerPurchasesResponse> findTopCustomersByOrders(int limit) {
        return orderRepository.findTopCustomersByOrders(limit);
    }

    private void decreaseStockForOrderItems(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            Integer productId = item.getProduct().getId();
            Integer quantity = item.getQuantity();
            productStockRepository.decreaseStock(productId, quantity);
        }
    }
}
