package dev.scastillo.ecommerce.product.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IncrementStockRequestDto {
    private final Integer quantity;
}
