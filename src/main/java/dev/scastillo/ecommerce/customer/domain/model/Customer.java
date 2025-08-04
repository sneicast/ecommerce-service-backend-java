package dev.scastillo.ecommerce.customer.domain.model;

import dev.scastillo.ecommerce.order.domain.model.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 60, nullable = false)
    private String firstName;
    @Column(length = 60, nullable = false)
    private String lastName;

    @Column(length = 255, nullable = true)
        private String address;

    @Column(length = 20, nullable = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
