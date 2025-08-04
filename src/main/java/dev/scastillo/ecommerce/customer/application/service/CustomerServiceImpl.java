package dev.scastillo.ecommerce.customer.application.service;

import dev.scastillo.ecommerce.customer.domain.model.Customer;
import dev.scastillo.ecommerce.customer.domain.repository.CustomerRepository;
import dev.scastillo.ecommerce.customer.domain.service.CustomerService;
import dev.scastillo.ecommerce.shared.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer crearCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerById(UUID customerId) {

        return customerRepository.findById(customerId).orElseThrow(() ->
            new NotFoundException("No se encontro el cliente con id: " + customerId)
        );
    }
}
