package dev.scastillo.ecommerce.order.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemCreateRequestDto {
    private Integer productId;
    private Integer quantity;
}
