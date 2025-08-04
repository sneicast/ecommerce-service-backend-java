package dev.scastillo.ecommerce.product.domain.repository;

import dev.scastillo.ecommerce.product.domain.model.ProductStock;

import java.util.Optional;

public interface ProductStockRepository {
    ProductStock saveStock(ProductStock productStock);
    Optional<ProductStock> findStockByProductId(Integer productId);
    void decreaseStock(Integer productId, Integer quantity);
    Integer getStockByProductId(Integer productId);
    void deleteStockByProductId(Integer productId);
    Integer incrementStock(Integer productId, Integer quantity);
    void deleteAll();
}
