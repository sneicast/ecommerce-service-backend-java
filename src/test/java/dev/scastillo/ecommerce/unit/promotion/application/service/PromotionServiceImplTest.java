package dev.scastillo.ecommerce.unit.promotion.application.service;

import dev.scastillo.ecommerce.promotion.application.service.PromotionServiceImpl;
import dev.scastillo.ecommerce.promotion.domain.model.Promotion;
import dev.scastillo.ecommerce.promotion.domain.repository.PromotionRepository;
import dev.scastillo.ecommerce.user.domain.model.User;
import dev.scastillo.ecommerce.user.domain.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class PromotionServiceImplTest {
    private final PromotionRepository promotionRepository = mock(PromotionRepository.class);
    private final UserService userService = mock(UserService.class);
    private final PromotionServiceImpl service = new PromotionServiceImpl(promotionRepository, userService);

    @Test
    void createPromotion_setsCreatedByAndSavesPromotion() {
        UUID userId = UUID.randomUUID();
        Promotion promotion = mock(Promotion.class);
        User user = mock(User.class);

        when(userService.getUserById(userId)).thenReturn(user);
        when(promotionRepository.save(promotion)).thenReturn(promotion);

        Promotion result = service.createPromotion(promotion, userId);

        verify(promotion).setCreatedBy(user);
        verify(promotionRepository).save(promotion);
        assertEquals(promotion, result);
    }

    @Test
    void findAllPromotions_returnsList() {
        List<Promotion> promotions = Collections.emptyList();
        when(promotionRepository.findAll()).thenReturn(promotions);

        List<Promotion> result = service.findAllPromotions();

        assertEquals(promotions, result);
        verify(promotionRepository).findAll();
    }
}
