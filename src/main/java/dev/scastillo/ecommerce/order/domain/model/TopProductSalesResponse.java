package dev.scastillo.ecommerce.order.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopProductSalesResponse {
    private Long productId;
    private String productName;
    private Long totalSales;
}
