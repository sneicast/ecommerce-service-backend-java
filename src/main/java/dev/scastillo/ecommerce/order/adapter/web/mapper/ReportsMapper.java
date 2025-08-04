package dev.scastillo.ecommerce.order.adapter.web.mapper;

import dev.scastillo.ecommerce.order.adapter.web.dto.TopCustomerPurchasesResponseDto;
import dev.scastillo.ecommerce.order.adapter.web.dto.TopProductSalesResponseDto;
import dev.scastillo.ecommerce.order.domain.model.TopCustomerPurchasesResponse;
import dev.scastillo.ecommerce.order.domain.model.TopProductSalesResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportsMapper {
    TopProductSalesResponseDto toDtoTopProductSales(TopProductSalesResponse topProductSalesResponse);
    TopCustomerPurchasesResponseDto toDtoTopCustomerPurchases(TopCustomerPurchasesResponse topCustomerPurchasesResponse);
}
