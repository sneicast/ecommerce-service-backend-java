package dev.scastillo.ecommerce.customer.infrastructure.repository;

import dev.scastillo.ecommerce.customer.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataCustomerRepository extends JpaRepository<Customer, UUID> {
}
