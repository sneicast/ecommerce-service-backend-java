package dev.scastillo.ecommerce.customer.adapter.web.mapper;

import dev.scastillo.ecommerce.customer.adapter.web.dto.CustomerCreateRequestDto;
import dev.scastillo.ecommerce.customer.adapter.web.dto.CustomerDto;
import dev.scastillo.ecommerce.customer.domain.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toDomain(CustomerCreateRequestDto customerDto);
    CustomerDto toDto(Customer customer);
}
