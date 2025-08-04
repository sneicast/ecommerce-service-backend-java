package dev.scastillo.ecommerce.product.infraestructure.repository;

import dev.scastillo.ecommerce.product.domain.model.Product;
import dev.scastillo.ecommerce.product.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JpaProductRepository implements ProductRepository {
    private final SpringDataProductRepository repository;

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> findByFilters(String title, String available) {
        return repository.findByFilters(title, available);
    }


    @Override
    public Optional<Product> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();

    }
}
