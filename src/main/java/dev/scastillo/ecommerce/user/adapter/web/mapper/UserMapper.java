package dev.scastillo.ecommerce.user.adapter.web.mapper;

import dev.scastillo.ecommerce.user.adapter.web.dto.UserCreateRequestDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserDto;
import dev.scastillo.ecommerce.user.adapter.web.dto.UserUpdateRequestDto;
import dev.scastillo.ecommerce.user.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomainCreate(UserCreateRequestDto dto);
    User toDomainUpdate(UserUpdateRequestDto dto);
    UserDto toDto(User user);
}
