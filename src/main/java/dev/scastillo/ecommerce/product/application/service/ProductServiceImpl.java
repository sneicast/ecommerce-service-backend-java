package dev.scastillo.ecommerce.product.application.service;

import dev.scastillo.ecommerce.product.domain.model.Product;
import dev.scastillo.ecommerce.product.domain.model.ProductStock;
import dev.scastillo.ecommerce.product.domain.repository.ProductRepository;
import dev.scastillo.ecommerce.product.domain.repository.ProductStockRepository;
import dev.scastillo.ecommerce.product.domain.service.ProductService;
import dev.scastillo.ecommerce.shared.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;

    @Override
    public List<Product> searchProducts(String title, boolean available) {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));
    }

    @Override
    public Product createProduct(Product product, Integer stockQuantity) {
        Product result = productRepository.save(product);
        ProductStock productStock = ProductStock.builder()
                .product(product)
                .quantity(stockQuantity)
                .build();
        productStockRepository.saveStock(productStock);
        return result;
    }

    @Override
    public Product updateProduct(Integer id, Product product) {
        Product existingProduct = getProductById(id);
        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setAvailable(product.isAvailable());
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public void incrementStock(Integer productId, Integer quantity) {
        productStockRepository.incrementStock(productId, quantity);
    }
}
