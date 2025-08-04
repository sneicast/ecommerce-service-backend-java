package dev.scastillo.ecommerce.customer.infrastructure.repository;

import dev.scastillo.ecommerce.customer.domain.model.Customer;
import dev.scastillo.ecommerce.customer.domain.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaCustomerRepository implements CustomerRepository {
    private final SpringDataCustomerRepository repository;

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return repository.findById(customerId);
    }
}
