package dev.scastillo.ecommerce.order.adapter.web.mapper;

import dev.scastillo.ecommerce.order.adapter.web.dto.OrderItemCreateRequestDto;
import dev.scastillo.ecommerce.order.adapter.web.dto.OrderItemDto;
import dev.scastillo.ecommerce.order.domain.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "productId", target = "product.id")
    OrderItem toDomain(OrderItemCreateRequestDto dto);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.title", target = "productTitle")
    OrderItemDto toDto(OrderItem domain);
}
