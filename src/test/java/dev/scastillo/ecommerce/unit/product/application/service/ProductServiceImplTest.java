package dev.scastillo.ecommerce.unit.product.application.service;

import dev.scastillo.ecommerce.product.application.service.ProductServiceImpl;
import dev.scastillo.ecommerce.product.domain.model.Product;
import dev.scastillo.ecommerce.product.domain.model.ProductStock;
import dev.scastillo.ecommerce.product.domain.repository.ProductRepository;
import dev.scastillo.ecommerce.product.domain.repository.ProductStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {
    private ProductRepository productRepository;
    private ProductStockRepository productStockRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productStockRepository = mock(ProductStockRepository.class);
        productService = new ProductServiceImpl(productRepository, productStockRepository);
    }


    @Test
    void searchProducts_returnsProducts() {
        Product product1 = Product.builder().id(1).title("Prod1").build();
        Product product2 = Product.builder().id(2).title("Prod2").build();
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.searchProducts("Prod", "true");

        assertEquals(products, result);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void searchProducts_returnsEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<Product> result = productService.searchProducts("Prod", "false");

        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_returnsProductWhenExists() {
        Product product = Product.builder().id(1).title("Prod1").build();
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(product));

        Product result = productService.getProductById(1);

        assertEquals(product, result);
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void getProductById_throwsNotFoundExceptionWhenNotExists() {
        when(productRepository.findById(2)).thenReturn(java.util.Optional.empty());

        assertThrows(dev.scastillo.ecommerce.shared.exception.NotFoundException.class, () -> productService.getProductById(2));
        verify(productRepository, times(1)).findById(2);
    }

    @Test
    void createProduct_createsProductAndStockSuccessfully() {
        Product product = Product.builder().id(1).title("Prod1").build();
        ProductStock productStock = ProductStock.builder().product(product).quantity(10).build();

        when(productRepository.save(product)).thenReturn(product);
        when(productStockRepository.saveStock(any(ProductStock.class))).thenReturn(productStock);

        Product result = productService.createProduct(product, 10);

        assertEquals(product, result);
        verify(productRepository, times(1)).save(product);
        verify(productStockRepository, times(1)).saveStock(any(ProductStock.class));
    }

    @Test
    void createProduct_throwsExceptionWhenProductSaveFails() {
        Product product = Product.builder().id(1).title("Prod1").build();
        when(productRepository.save(product)).thenThrow(new RuntimeException("Error al guardar producto"));

        assertThrows(RuntimeException.class, () -> productService.createProduct(product, 10));
        verify(productRepository, times(1)).save(product);
        verify(productStockRepository, never()).saveStock(any(ProductStock.class));
    }

    @Test
    void updateProduct_updatesExistingProductSuccessfully() {
        Product existingProduct = Product.builder()
                .id(1)
                .title("Old Title")
                .description("Old Desc")
                .imageUrl("old.jpg")
                .price(new BigDecimal(100.0))
                .available(false)
                .build();

        Product updatedProduct = Product.builder()
                .title("New Title")
                .description("New Desc")
                .imageUrl("new.jpg")
                .price(new BigDecimal(150.0))
                .available(true)
                .build();

        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.updateProduct(1, updatedProduct);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Desc", result.getDescription());
        assertEquals("new.jpg", result.getImageUrl());
        assertEquals(new BigDecimal(150.0), result.getPrice());
        assertTrue(result.isAvailable());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void updateProduct_throwsNotFoundExceptionWhenProductNotExists() {
        Product updatedProduct = Product.builder().title("New Title").build();
        when(productRepository.findById(2)).thenReturn(java.util.Optional.empty());

        assertThrows(dev.scastillo.ecommerce.shared.exception.NotFoundException.class, () -> productService.updateProduct(2, updatedProduct));
        verify(productRepository, times(1)).findById(2);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void deleteProduct_callsRepositoryDeleteById() {
        productService.deleteProduct(1);
        verify(productRepository, times(1)).deleteById(1);
    }
}
