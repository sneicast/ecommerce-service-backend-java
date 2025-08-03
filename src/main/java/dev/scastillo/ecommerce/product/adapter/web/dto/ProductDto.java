package dev.scastillo.ecommerce.product.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private double price;
    private boolean available;
    private Integer stockQuantity;
}
