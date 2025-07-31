package dev.scastillo.ecommerce.user.adapter.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private Boolean status;
    private String password;
}
