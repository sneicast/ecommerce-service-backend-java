package dev.scastillo.ecommerce.product.infraestructure.repository;

import dev.scastillo.ecommerce.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductRepository extends JpaRepository<Product, Integer> {
}
