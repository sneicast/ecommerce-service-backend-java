package dev.scastillo.ecommerce.promotion.domain.repository;

import dev.scastillo.ecommerce.promotion.domain.model.Promotion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PromotionRepository {
    List<Promotion> findAll();
    Promotion save(Promotion promotion);
    Optional<Promotion> findActivePromotionByDate(LocalDate date);
}
