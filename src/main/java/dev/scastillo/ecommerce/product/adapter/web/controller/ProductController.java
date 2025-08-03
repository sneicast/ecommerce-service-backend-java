package dev.scastillo.ecommerce.product.adapter.web.controller;

import dev.scastillo.ecommerce.product.adapter.web.dto.ProductCreateRequestDto;
import dev.scastillo.ecommerce.product.adapter.web.dto.ProductDto;
import dev.scastillo.ecommerce.product.adapter.web.dto.ProductUpdateRequestDto;
import dev.scastillo.ecommerce.product.adapter.web.mapper.ProductMapper;
import dev.scastillo.ecommerce.product.domain.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductCreateRequestDto request){
        return productMapper.toDto(
                productService.createProduct(
                        productMapper.toDomainCreateProduct(request), request.getStockQuantity()
                )
        );
    }

    @GetMapping
    public List<ProductDto> searchProducts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean available
    ) {
        String searchTitle = title != null ? title : "";
        Boolean searchAvailable = available != null ? available : true;

        return productService.searchProducts(searchTitle, searchAvailable).stream()
                .map(productMapper::toDto)
                .toList();
    }
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return productMapper.toDto(productService.getProductById(id));
    }
    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Integer id, @RequestBody ProductUpdateRequestDto request) {
        return productMapper.toDto(
                productService.updateProduct(id, productMapper.toDomainUpdateProduct(request))
        );
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }
}
