package dev.scastillo.ecommerce.order.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TopCustomerPurchasesResponseDto {
    private UUID customerId;
    private String fullName;
    private Long totalPurchases;
}
