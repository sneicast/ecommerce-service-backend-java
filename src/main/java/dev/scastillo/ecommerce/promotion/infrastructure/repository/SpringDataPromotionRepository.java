package dev.scastillo.ecommerce.promotion.infrastructure.repository;

import dev.scastillo.ecommerce.promotion.domain.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataPromotionRepository extends JpaRepository<Promotion, UUID> {

    @Query("SELECT p FROM Promotion p WHERE :date BETWEEN p.startDate AND p.endDate ORDER BY p.createdAt DESC LIMIT 1")
    Optional<Promotion> findActivePromotion(@Param("date") LocalDate date);

}
