package dev.scastillo.ecommerce.promotion.adapter.web.controller;

import dev.scastillo.ecommerce.promotion.adapter.web.dto.PromotionCreateRequestDto;
import dev.scastillo.ecommerce.promotion.adapter.web.dto.PromotionDto;
import dev.scastillo.ecommerce.promotion.adapter.web.mapper.PromotionMapper;
import dev.scastillo.ecommerce.promotion.domain.service.PromotionService;
import dev.scastillo.ecommerce.shared.auth.AuthenticatedUserId;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/promotions")
@AllArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;
    private final PromotionMapper promotionMapper;


    @PostMapping
    public PromotionDto createPromotion(@RequestBody PromotionCreateRequestDto request, @AuthenticatedUserId UUID userId){
        return promotionMapper.toDto(promotionService.createPromotion(promotionMapper.toDomain(request), userId));
    }

    @GetMapping
    public List<PromotionDto> getAllPromotions() {
        return promotionService.findAllPromotions().stream()
                .map(promotionMapper::toDto)
                .toList();
    }
}
