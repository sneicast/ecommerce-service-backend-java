package dev.scastillo.ecommerce.unit.product.adapter.web.controller;

import dev.scastillo.ecommerce.product.adapter.web.controller.ProductController;
import dev.scastillo.ecommerce.product.adapter.web.dto.ProductCreateRequestDto;
import dev.scastillo.ecommerce.product.adapter.web.dto.ProductDto;
import dev.scastillo.ecommerce.product.adapter.web.dto.ProductUpdateRequestDto;
import dev.scastillo.ecommerce.product.adapter.web.mapper.ProductMapper;
import dev.scastillo.ecommerce.product.domain.model.Product;
import dev.scastillo.ecommerce.product.domain.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {
    private ProductService productService;
    private ProductMapper productMapper;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productMapper = mock(ProductMapper.class);
        productController = new ProductController(productService, productMapper);
    }

    @Test
    void createProduct_returnsProductDto_whenProductIsCreated() {
        ProductCreateRequestDto requestDto = new ProductCreateRequestDto();
        requestDto.setStockQuantity(5);
        // Configura el resto de campos necesarios en requestDto

        var domainProduct = mock(Product.class);
        var productDto = mock(ProductDto.class);

        when(productMapper.toDomainCreateProduct(requestDto)).thenReturn(domainProduct);
        when(productService.createProduct(domainProduct, 5)).thenReturn(domainProduct);
        when(productMapper.toDto(domainProduct)).thenReturn(productDto);

        ProductDto result = productController.createProduct(requestDto);

        assertEquals(productDto, result);
        verify(productMapper).toDomainCreateProduct(requestDto);
        verify(productService).createProduct(domainProduct, 5);
        verify(productMapper).toDto(domainProduct);
    }

    @Test
    void searchProducts_returnsMappedProductDtoList_whenCalledWithParams() {
        var domainProduct = mock(Product.class);
        var productDto = mock(ProductDto.class);

        when(productService.searchProducts("test", "false")).thenReturn(List.of(domainProduct));
        when(productMapper.toDto(domainProduct)).thenReturn(productDto);

        List<ProductDto> result = productController.searchProducts("test", "false");

        assertEquals(1, result.size());
        assertEquals(productDto, result.get(0));
        verify(productService).searchProducts("test", "false");
        verify(productMapper).toDto(domainProduct);
    }

    @Test
    void searchProducts_usesDefaultValues_whenParamsAreNull() {
        var domainProduct = mock(Product.class);
        var productDto = mock(ProductDto.class);

        when(productService.searchProducts("", "true")).thenReturn(List.of(domainProduct));
        when(productMapper.toDto(domainProduct)).thenReturn(productDto);

        List<ProductDto> result = productController.searchProducts(null, null);

        assertEquals(1, result.size());
        assertEquals(productDto, result.get(0));
        verify(productService).searchProducts("", "true");
        verify(productMapper).toDto(domainProduct);
    }

    @Test
    void getProductById_returnsMappedProductDto() {
        var domainProduct = mock(Product.class);
        var productDto = mock(ProductDto.class);

        when(productService.getProductById(1)).thenReturn(domainProduct);
        when(productMapper.toDto(domainProduct)).thenReturn(productDto);

        ProductDto result = productController.getProductById(1);

        assertEquals(productDto, result);
        verify(productService).getProductById(1);
        verify(productMapper).toDto(domainProduct);
    }

    @Test
    void updateProduct_returnsMappedProductDto() {
        var domainProduct = mock(Product.class);
        var productDto = mock(ProductDto.class);
        var updateRequest = mock(ProductUpdateRequestDto.class);

        when(productMapper.toDomainUpdateProduct(updateRequest)).thenReturn(domainProduct);
        when(productService.updateProduct(1, domainProduct)).thenReturn(domainProduct);
        when(productMapper.toDto(domainProduct)).thenReturn(productDto);

        ProductDto result = productController.updateProduct(1, updateRequest);

        assertEquals(productDto, result);
        verify(productMapper).toDomainUpdateProduct(updateRequest);
        verify(productService).updateProduct(1, domainProduct);
        verify(productMapper).toDto(domainProduct);
    }

    @Test
    void deleteProduct_callsServiceDeleteProduct() {
        productController.deleteProduct(1);
        verify(productService).deleteProduct(1);
    }


}
