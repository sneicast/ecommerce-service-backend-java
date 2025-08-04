package dev.scastillo.ecommerce.order.adapter.web.controller;

import dev.scastillo.ecommerce.order.adapter.web.dto.TopCustomerPurchasesResponseDto;
import dev.scastillo.ecommerce.order.adapter.web.dto.TopProductSalesResponseDto;
import dev.scastillo.ecommerce.order.adapter.web.mapper.ReportsMapper;
import dev.scastillo.ecommerce.order.domain.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@AllArgsConstructor
public class ReportsController {
    private final OrderService orderService;
    private final ReportsMapper reportsMapper;

    @GetMapping("/top-products")
    public List<TopProductSalesResponseDto> getTopProductSales(@RequestParam Integer limit) {
        return orderService.findTopProductsBySales(limit).stream()
                .map(reportsMapper::toDtoTopProductSales)
                .toList();
    }
    @GetMapping("/top-customers")
    public List<TopCustomerPurchasesResponseDto> getTopCustomerPurchases(@RequestParam Integer limit) {
        return orderService.findTopCustomersByOrders(limit).stream()
                .map(reportsMapper::toDtoTopCustomerPurchases)
                .toList();
    }
}
