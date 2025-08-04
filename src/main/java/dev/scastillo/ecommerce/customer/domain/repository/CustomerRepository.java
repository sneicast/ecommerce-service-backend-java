package dev.scastillo.ecommerce.customer.domain.repository;

import dev.scastillo.ecommerce.customer.domain.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    List<Customer> findAll();
    Customer save(Customer customer);
    Optional<Customer> findById(UUID customerId);
}
