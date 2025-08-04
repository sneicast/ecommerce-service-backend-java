package dev.scastillo.ecommerce.unit.promotion.infrastructure.repository;

import dev.scastillo.ecommerce.promotion.domain.model.Promotion;
import dev.scastillo.ecommerce.promotion.infrastructure.repository.JpaPromotionRepository;
import dev.scastillo.ecommerce.promotion.infrastructure.repository.SpringDataPromotionRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class JpaPromotionRepositoryTest {
    private final SpringDataPromotionRepository springRepo = mock(SpringDataPromotionRepository.class);
    private final JpaPromotionRepository repository = new JpaPromotionRepository(springRepo);

    @Test
    void findAll_returnsList() {
        List<Promotion> promotions = Collections.emptyList();
        when(springRepo.findAll()).thenReturn(promotions);

        List<Promotion> result = repository.findAll();

        assertEquals(promotions, result);
        verify(springRepo, times(1)).findAll();
    }

    @Test
    void save_returnsSavedPromotion() {
        Promotion promotion = mock(Promotion.class);
        when(springRepo.save(promotion)).thenReturn(promotion);

        Promotion result = repository.save(promotion);

        assertEquals(promotion, result);
        verify(springRepo, times(1)).save(promotion);
    }

    @Test
    void findActivePromotionByDate_returnsOptional() {
        LocalDate date = LocalDate.now();
        Promotion promotion = mock(Promotion.class);
        Optional<Promotion> expected = Optional.of(promotion);
        when(springRepo.findActivePromotion(date)).thenReturn(expected);

        Optional<Promotion> result = repository.findActivePromotionByDate(date);

        assertEquals(expected, result);
        verify(springRepo, times(1)).findActivePromotion(date);
    }
}
