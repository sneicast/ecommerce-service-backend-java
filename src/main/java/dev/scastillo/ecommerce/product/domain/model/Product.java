package dev.scastillo.ecommerce.product.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 100, nullable = false, unique = true)
    private String title;

    @Column(length = 255, nullable = false)
    private String description;

    @Column(length = 255, nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private boolean available;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
    private ProductStock stock;
}
