package dev.scastillo.ecommerce.order.adapter.web.mapper;

import dev.scastillo.ecommerce.customer.domain.model.Customer;
import dev.scastillo.ecommerce.order.adapter.web.dto.OrderCreateRequestDto;
import dev.scastillo.ecommerce.order.adapter.web.dto.OrderDto;
import dev.scastillo.ecommerce.order.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "customerId", target = "customer.id")
    Order toDomain(OrderCreateRequestDto orderDto);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(target = "customerName", expression = "java(customerFullname(order.getCustomer()))")
    @Mapping(source = "customer.phoneNumber", target = "customerPhone")
    OrderDto toDto(Order order);


    @Named("customerFullname")
    default String customerFullname(Customer customer) {
        return customer.getFirstName() + " " + customer.getLastName();
    }
}
