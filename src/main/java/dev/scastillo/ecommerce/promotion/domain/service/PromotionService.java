package dev.scastillo.ecommerce.promotion.domain.service;

import dev.scastillo.ecommerce.promotion.domain.model.Promotion;

import java.util.List;
import java.util.UUID;

public interface PromotionService {
    Promotion createPromotion(Promotion promotion, UUID userId);
    List<Promotion> findAllPromotions();
}
