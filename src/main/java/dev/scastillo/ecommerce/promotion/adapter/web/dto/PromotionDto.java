package dev.scastillo.ecommerce.promotion.adapter.web.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionDto {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal globalDiscountPercentage;
    private BigDecimal randomOrderDiscountPercentage;
    private OffsetDateTime createdAt;
    private UUID createdById;
    private String createdByFullName;
}
