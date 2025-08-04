package dev.scastillo.ecommerce.order.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequestDto {
    private UUID customerId;
    private OrderItemCreateRequestDto[] items;
}
