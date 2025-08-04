package dev.scastillo.ecommerce.product.domain.service;

import dev.scastillo.ecommerce.product.domain.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> searchProducts(String title, boolean available);
    Product getProductById(Integer id);
    Product createProduct(Product product, Integer stockQuantity);
    Product updateProduct(Integer id, Product product);
    void deleteProduct(Integer id);
    void incrementStock(Integer productId, Integer quantity);

}
