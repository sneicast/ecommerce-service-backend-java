package dev.scastillo.ecommerce.product.infraestructure.repository;

import dev.scastillo.ecommerce.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p " +
            "WHERE (:title IS NULL OR :title = '' OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:available IS NULL OR :available = '' OR p.available = CASE WHEN :available = 'true' THEN true WHEN :available = 'false' THEN false ELSE p.available END)")
    List<Product> findByFilters(@Param("title") String title, @Param("available") String available);
}
