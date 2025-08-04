package dev.scastillo.ecommerce.customer.adapter.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCreateRequestDto {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
}
