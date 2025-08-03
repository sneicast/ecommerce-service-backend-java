package dev.scastillo.ecommerce.product.adapter.web.mapper;

import dev.scastillo.ecommerce.product.adapter.web.dto.ProductCreateRequestDto;
import dev.scastillo.ecommerce.product.adapter.web.dto.ProductDto;
import dev.scastillo.ecommerce.product.adapter.web.dto.ProductUpdateRequestDto;
import dev.scastillo.ecommerce.product.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDomainCreateProduct(ProductCreateRequestDto dto);
    Product toDomainUpdateProduct(ProductUpdateRequestDto dto);
    @Mapping(source = "stock.quantity", target = "stockQuantity" )
    ProductDto toDto(Product product);
}
