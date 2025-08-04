package dev.scastillo.ecommerce.customer.domain.service;

import dev.scastillo.ecommerce.customer.domain.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> findAllCustomers();
    Customer crearCustomer(Customer customer);
    Customer findCustomerById(UUID customerId);
}
