package dev.scastillo.ecommerce.promotion.adapter.web.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionCreateRequestDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal globalDiscountPercentage;
    private BigDecimal randomOrderDiscountPercentage;
}
