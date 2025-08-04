package dev.scastillo.ecommerce.product.domain.repository;

import dev.scastillo.ecommerce.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    List<Product> findByFilters(String title, String available);
    Optional<Product> findById(Integer id);
    Product save(Product product);
    void deleteById(Integer id);
    void deleteAll();

}
