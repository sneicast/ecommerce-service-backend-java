package dev.scastillo.ecommerce.order.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private BigInteger id;
    private Integer productId;
    private String productTitle;
    private Integer quantity;
    private BigDecimal unitPrice;

}
