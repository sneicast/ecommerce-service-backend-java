package dev.scastillo.ecommerce.promotion.infrastructure.repository;

import dev.scastillo.ecommerce.promotion.domain.model.Promotion;
import dev.scastillo.ecommerce.promotion.domain.repository.PromotionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JpaPromotionRepository implements PromotionRepository {
    private final SpringDataPromotionRepository repository;
    @Override
    public List<Promotion> findAll() {
        return repository.findAll();
    }

    @Override
    public Promotion save(Promotion promotion) {
        return repository.save(promotion);
    }

    @Override
    public Optional<Promotion> findActivePromotionByDate(LocalDate date) {
        return repository.findActivePromotion(date);
    }
}
