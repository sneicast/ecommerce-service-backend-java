package dev.scastillo.ecommerce.user.adapter.web.mapper;

import dev.scastillo.ecommerce.user.adapter.web.dto.LoginResponseDto;
import dev.scastillo.ecommerce.user.domain.service.dto.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    LoginResponseDto  toLoginResponseDto(LoginResponse loginResponse);
}
