package dev.scastillo.ecommerce.order.adapter.web.dto;

import dev.scastillo.ecommerce.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private BigInteger id;
    private UUID customerId;
    private String customerName;
    private String customerPhone;
    private OffsetDateTime orderDate;
    private BigDecimal totalAmount;
    private User createdBy;
    private String createdByName;
    private OrderItemDto[] items;
}
