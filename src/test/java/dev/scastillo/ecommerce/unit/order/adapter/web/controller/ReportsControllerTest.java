package dev.scastillo.ecommerce.unit.order.adapter.web.controller;

import dev.scastillo.ecommerce.order.adapter.web.controller.ReportsController;
import dev.scastillo.ecommerce.order.adapter.web.dto.TopCustomerPurchasesResponseDto;
import dev.scastillo.ecommerce.order.adapter.web.dto.TopProductSalesResponseDto;
import dev.scastillo.ecommerce.order.adapter.web.mapper.ReportsMapper;
import dev.scastillo.ecommerce.order.domain.model.TopCustomerPurchasesResponse;
import dev.scastillo.ecommerce.order.domain.model.TopProductSalesResponse;
import dev.scastillo.ecommerce.order.domain.service.OrderService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class ReportsControllerTest {
    private final OrderService orderService = mock(OrderService.class);
    private final ReportsMapper reportsMapper = mock(ReportsMapper.class);
    private final ReportsController controller = new ReportsController(orderService, reportsMapper);

    @Test
    void getTopProductSales_returnsDtoList() {
        int limit = 3;
        TopProductSalesResponse domainObj = mock(TopProductSalesResponse.class);
        TopProductSalesResponseDto dto = mock(TopProductSalesResponseDto.class);

        when(orderService.findTopProductsBySales(limit)).thenReturn(List.of(domainObj));
        when(reportsMapper.toDtoTopProductSales(domainObj)).thenReturn(dto);

        List<TopProductSalesResponseDto> result = controller.getTopProductSales(limit);

        assertEquals(List.of(dto), result);
        verify(orderService).findTopProductsBySales(limit);
        verify(reportsMapper).toDtoTopProductSales(domainObj);
    }

    @Test
    void getTopCustomerPurchases_returnsDtoList() {
        int limit = 2;
        TopCustomerPurchasesResponse domainObj = mock(TopCustomerPurchasesResponse.class);
        TopCustomerPurchasesResponseDto dto = mock(TopCustomerPurchasesResponseDto.class);

        when(orderService.findTopCustomersByOrders(limit)).thenReturn(List.of(domainObj));
        when(reportsMapper.toDtoTopCustomerPurchases(domainObj)).thenReturn(dto);

        List<TopCustomerPurchasesResponseDto> result = controller.getTopCustomerPurchases(limit);

        assertEquals(List.of(dto), result);
        verify(orderService).findTopCustomersByOrders(limit);
        verify(reportsMapper).toDtoTopCustomerPurchases(domainObj);
    }
}
