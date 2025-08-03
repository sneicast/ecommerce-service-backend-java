package dev.scastillo.ecommerce.product.infraestructure.repository;

import dev.scastillo.ecommerce.product.domain.model.ProductStock;
import dev.scastillo.ecommerce.product.domain.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class JpaProductStockRepository implements ProductStockRepository {
    private final SpringDataProductStockRepository repository;


    @Override
    public ProductStock saveStock(ProductStock productStock) {
        return repository.save(productStock);
    }

    @Override
    public Optional<ProductStock> findStockByProductId(Integer productId) {
        return repository.findByProductId(productId);
    }

    @Override
    public void decreaseStock(Integer productId, Integer quantity) {
        ProductStock productStock = repository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Product stock not found for ID: " + productId));

        if (productStock.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product ID: " + productId);
        }

        productStock.setQuantity(productStock.getQuantity() - quantity);
        repository.save(productStock);
    }

    @Override
    public Integer getStockByProductId(Integer productId) {
        return repository.findByProductId(productId)
                .map(ProductStock::getQuantity)
                .orElseThrow(() -> new RuntimeException("Product stock not found for ID: " + productId));
    }

    @Override
    public void deleteStockByProductId(Integer productId) {
        repository.deleteById(productId);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
