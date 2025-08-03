package dev.scastillo.ecommerce.unit.product.infraestructure.repository;

import dev.scastillo.ecommerce.product.domain.model.Product;
import dev.scastillo.ecommerce.product.infraestructure.repository.JpaProductRepository;
import dev.scastillo.ecommerce.product.infraestructure.repository.SpringDataProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class JpaProductRepositoryTest {

    private SpringDataProductRepository springDataProductRepository;
    private JpaProductRepository jpaProductRepository;

    @BeforeEach
    void setUp() {
        springDataProductRepository = mock(SpringDataProductRepository.class);
        jpaProductRepository = new JpaProductRepository(springDataProductRepository);
    }

    @Test
    void findAll_returnsAllProducts() {
        Product product1 = Product.builder().id(1).title("Prod1").build();
        Product product2 = Product.builder().id(2).title("Prod2").build();
        List<Product> products = Arrays.asList(product1, product2);

        when(springDataProductRepository.findAll()).thenReturn(products);

        List<Product> result = jpaProductRepository.findAll();

        assertEquals(products, result);
        verify(springDataProductRepository, times(1)).findAll();
    }

    @Test
    void findById_returnsProductWhenExists() {
        Product product = Product.builder().id(1).title("Prod1").build();
        when(springDataProductRepository.findById(1)).thenReturn(Optional.of(product));

        Optional<Product> result = jpaProductRepository.findById(1);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(springDataProductRepository, times(1)).findById(1);
    }

    @Test
    void findById_returnsEmptyWhenNotExists() {
        when(springDataProductRepository.findById(2)).thenReturn(Optional.empty());

        Optional<Product> result = jpaProductRepository.findById(2);

        assertFalse(result.isPresent());
        verify(springDataProductRepository, times(1)).findById(2);
    }

    @Test
    void save_returnsSavedProduct() {
        Product product = Product.builder().id(1).title("Prod1").build();
        when(springDataProductRepository.save(product)).thenReturn(product);

        Product result = jpaProductRepository.save(product);

        assertEquals(product, result);
        verify(springDataProductRepository, times(1)).save(product);
    }

    @Test
    void save_throwsExceptionWhenRepositoryFails() {
        Product product = Product.builder().id(1).title("Prod1").build();
        when(springDataProductRepository.save(product)).thenThrow(new RuntimeException("Error al guardar"));

        assertThrows(RuntimeException.class, () -> jpaProductRepository.save(product));
        verify(springDataProductRepository, times(1)).save(product);
    }

    @Test
    void deleteById_callsRepositoryDeleteById() {
        jpaProductRepository.deleteById(1);
        verify(springDataProductRepository, times(1)).deleteById(1);
    }
}
