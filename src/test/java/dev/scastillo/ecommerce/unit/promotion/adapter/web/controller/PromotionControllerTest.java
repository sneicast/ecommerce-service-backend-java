package dev.scastillo.ecommerce.unit.promotion.adapter.web.controller;

import dev.scastillo.ecommerce.promotion.adapter.web.controller.PromotionController;
import dev.scastillo.ecommerce.promotion.adapter.web.dto.PromotionCreateRequestDto;
import dev.scastillo.ecommerce.promotion.adapter.web.dto.PromotionDto;
import dev.scastillo.ecommerce.promotion.adapter.web.mapper.PromotionMapper;
import dev.scastillo.ecommerce.promotion.domain.model.Promotion;
import dev.scastillo.ecommerce.promotion.domain.service.PromotionService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class PromotionControllerTest {
    private final PromotionService promotionService = mock(PromotionService.class);
    private final PromotionMapper promotionMapper = mock(PromotionMapper.class);
    private final PromotionController controller = new PromotionController(promotionService, promotionMapper);

    @Test
    void createPromotion_returnsDto() {
        PromotionCreateRequestDto request = mock(PromotionCreateRequestDto.class);
        UUID userId = UUID.randomUUID();
        Promotion promotion = mock(Promotion.class);
        PromotionDto dto = mock(PromotionDto.class);

        when(promotionMapper.toDomain(request)).thenReturn(promotion);
        when(promotionService.createPromotion(promotion, userId)).thenReturn(promotion);
        when(promotionMapper.toDto(promotion)).thenReturn(dto);

        PromotionDto result = controller.createPromotion(request, userId);

        assertEquals(dto, result);
        verify(promotionMapper).toDomain(request);
        verify(promotionService).createPromotion(promotion, userId);
        verify(promotionMapper).toDto(promotion);
    }

    @Test
    void getAllPromotions_returnsListOfDtos() {
        Promotion promotion = mock(Promotion.class);
        PromotionDto dto = mock(PromotionDto.class);
        List<Promotion> promotions = List.of(promotion);
        List<PromotionDto> dtos = List.of(dto);

        when(promotionService.findAllPromotions()).thenReturn(promotions);
        when(promotionMapper.toDto(promotion)).thenReturn(dto);

        List<PromotionDto> result = controller.getAllPromotions();

        assertEquals(dtos, result);
        verify(promotionService).findAllPromotions();
        verify(promotionMapper).toDto(promotion);
    }
}
