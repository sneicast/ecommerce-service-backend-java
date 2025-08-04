package dev.scastillo.ecommerce.order.adapter.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopProductSalesResponseDto {
    private Long productId;
    private String productName;
    private Long totalSales;
}
