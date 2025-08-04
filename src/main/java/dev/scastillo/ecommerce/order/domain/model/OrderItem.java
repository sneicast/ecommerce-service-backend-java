package dev.scastillo.ecommerce.order.domain.model;

import dev.scastillo.ecommerce.product.domain.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue
    private BigInteger id;

//    @Column(name = "order_id", nullable = false)
//    private BigInteger orderId;

    @ManyToOne
//    @MapsId
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

//    @Column(name = "product_id", nullable = false)
//    private Integer productId;

    @ManyToOne
//    @MapsId
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
}
