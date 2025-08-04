package dev.scastillo.ecommerce.customer.adapter.web.controller;

import dev.scastillo.ecommerce.customer.adapter.web.dto.CustomerCreateRequestDto;
import dev.scastillo.ecommerce.customer.adapter.web.dto.CustomerDto;
import dev.scastillo.ecommerce.customer.adapter.web.mapper.CustomerMapper;
import dev.scastillo.ecommerce.customer.domain.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping
    public CustomerDto createCustomer(@RequestBody CustomerCreateRequestDto request){
        return customerMapper.toDto(customerService.crearCustomer(customerMapper.toDomain(request)));
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.findAllCustomers()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }
}
