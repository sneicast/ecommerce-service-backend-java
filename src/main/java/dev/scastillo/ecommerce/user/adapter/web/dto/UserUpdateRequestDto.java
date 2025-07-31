package dev.scastillo.ecommerce.user.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private Boolean status;
}
