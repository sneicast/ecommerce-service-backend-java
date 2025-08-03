package dev.scastillo.ecommerce.unit.product.infraestructure.repository;

import dev.scastillo.ecommerce.product.domain.model.ProductStock;
import dev.scastillo.ecommerce.product.infraestructure.repository.JpaProductStockRepository;
import dev.scastillo.ecommerce.product.infraestructure.repository.SpringDataProductStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class JpaProductStockRepositoryTest {

    private SpringDataProductStockRepository springDataProductStockRepository;
    private JpaProductStockRepository jpaProductStockRepository;

    @BeforeEach
    void setUp() {
        springDataProductStockRepository = mock(SpringDataProductStockRepository.class);
        jpaProductStockRepository = new JpaProductStockRepository(springDataProductStockRepository);
    }

    @Test
    void saveStock_callsRepositorySaveAndReturnsProductStock() {
        ProductStock productStock = ProductStock.builder().productId(1).quantity(10).build();
        when(springDataProductStockRepository.save(productStock)).thenReturn(productStock);

        ProductStock result = jpaProductStockRepository.saveStock(productStock);

        assertEquals(productStock, result);
        verify(springDataProductStockRepository, times(1)).save(productStock);
    }

    @Test
    void findStockByProductId_returnsProductStockWhenExists() {
        ProductStock productStock = ProductStock.builder().productId(1).quantity(10).build();
        when(springDataProductStockRepository.findByProductId(1)).thenReturn(Optional.of(productStock));

        Optional<ProductStock> result = jpaProductStockRepository.findStockByProductId(1);

        assertTrue(result.isPresent());
        assertEquals(productStock, result.get());
        verify(springDataProductStockRepository, times(1)).findByProductId(1);
    }

    @Test
    void findStockByProductId_returnsEmptyWhenNotExists() {
        when(springDataProductStockRepository.findByProductId(2)).thenReturn(Optional.empty());

        Optional<ProductStock> result = jpaProductStockRepository.findStockByProductId(2);

        assertFalse(result.isPresent());
        verify(springDataProductStockRepository, times(1)).findByProductId(2);
    }

    @Test
    void decreaseStock_decreasesStockSuccessfully() {
        ProductStock productStock = ProductStock.builder().productId(1).quantity(10).build();
        when(springDataProductStockRepository.findByProductId(1)).thenReturn(Optional.of(productStock));
        when(springDataProductStockRepository.save(any(ProductStock.class))).thenReturn(productStock);

        jpaProductStockRepository.decreaseStock(1, 5);

        assertEquals(5, productStock.getQuantity());
        verify(springDataProductStockRepository, times(1)).findByProductId(1);
        verify(springDataProductStockRepository, times(1)).save(productStock);
    }

    @Test
    void decreaseStock_throwsNotFoundExceptionWhenStockNotExists() {
        when(springDataProductStockRepository.findByProductId(2)).thenReturn(Optional.empty());

        assertThrows(dev.scastillo.ecommerce.shared.exception.NotFoundException.class,
                () -> jpaProductStockRepository.decreaseStock(2, 5));
        verify(springDataProductStockRepository, times(1)).findByProductId(2);
        verify(springDataProductStockRepository, never()).save(any(ProductStock.class));
    }

    @Test
    void decreaseStock_throwsConflictExceptionWhenInsufficientStock() {
        ProductStock productStock = ProductStock.builder().productId(3).quantity(4).build();
        when(springDataProductStockRepository.findByProductId(3)).thenReturn(Optional.of(productStock));

        assertThrows(dev.scastillo.ecommerce.shared.exception.ConflictException.class,
                () -> jpaProductStockRepository.decreaseStock(3, 10));
        verify(springDataProductStockRepository, times(1)).findByProductId(3);
        verify(springDataProductStockRepository, never()).save(any(ProductStock.class));
    }

    @Test
    void getStockByProductId_returnsQuantityWhenStockExists() {
        ProductStock productStock = ProductStock.builder().productId(1).quantity(15).build();
        when(springDataProductStockRepository.findByProductId(1)).thenReturn(Optional.of(productStock));

        Integer quantity = jpaProductStockRepository.getStockByProductId(1);

        assertEquals(15, quantity);
        verify(springDataProductStockRepository, times(1)).findByProductId(1);
    }

    @Test
    void getStockByProductId_throwsNotFoundExceptionWhenStockNotExists() {
        when(springDataProductStockRepository.findByProductId(2)).thenReturn(Optional.empty());

        assertThrows(dev.scastillo.ecommerce.shared.exception.NotFoundException.class,
                () -> jpaProductStockRepository.getStockByProductId(2));
        verify(springDataProductStockRepository, times(1)).findByProductId(2);
    }

    @Test
    void deleteStockByProductId_callsRepositoryDeleteById() {
        jpaProductStockRepository.deleteStockByProductId(1);
        verify(springDataProductStockRepository, times(1)).deleteById(1);
    }


}
