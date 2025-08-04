package dev.scastillo.ecommerce.promotion.adapter.web.mapper;
import dev.scastillo.ecommerce.promotion.adapter.web.dto.PromotionCreateRequestDto;
import dev.scastillo.ecommerce.promotion.adapter.web.dto.PromotionDto;
import dev.scastillo.ecommerce.promotion.domain.model.Promotion;
import dev.scastillo.ecommerce.user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    Promotion toDomain(PromotionCreateRequestDto dto);

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(target = "createdByFullName", expression = "java(userFullname(domain.getCreatedBy()))")
    PromotionDto toDto(Promotion domain);

    @Named("userFullname")
    default String userFullname(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
