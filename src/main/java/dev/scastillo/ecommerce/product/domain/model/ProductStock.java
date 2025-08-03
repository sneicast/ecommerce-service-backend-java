package dev.scastillo.ecommerce.product.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStock {
    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(nullable = false)
    private Integer quantity;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

}
