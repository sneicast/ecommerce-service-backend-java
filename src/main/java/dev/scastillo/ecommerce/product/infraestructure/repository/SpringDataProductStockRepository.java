package dev.scastillo.ecommerce.product.infraestructure.repository;

import dev.scastillo.ecommerce.product.domain.model.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataProductStockRepository extends JpaRepository<ProductStock, Integer> {
    Optional<ProductStock> findByProductId(Integer productId);
    void deleteByProductId(Integer productId);
}
