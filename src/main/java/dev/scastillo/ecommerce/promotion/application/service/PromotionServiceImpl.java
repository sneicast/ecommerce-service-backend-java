package dev.scastillo.ecommerce.promotion.application.service;

import dev.scastillo.ecommerce.promotion.domain.model.Promotion;
import dev.scastillo.ecommerce.promotion.domain.repository.PromotionRepository;
import dev.scastillo.ecommerce.promotion.domain.service.PromotionService;
import dev.scastillo.ecommerce.user.domain.model.User;
import dev.scastillo.ecommerce.user.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final UserService userService;

    @Override
    public Promotion createPromotion(Promotion promotion, UUID userId) {
        User user = this.userService.getUserById(userId);
        promotion.setCreatedBy(user);
        return promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> findAllPromotions() {
        return promotionRepository.findAll();
    }
}
