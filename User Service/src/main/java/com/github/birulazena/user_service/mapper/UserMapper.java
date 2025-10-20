package com.github.birulazena.user_service.mapper;

import com.github.birulazena.user_service.dto.UserRequestDto;
import com.github.birulazena.user_service.dto.UserResponseDto;
import com.github.birulazena.user_service.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cards", source = "cards")
    User toEntity(UserRequestDto userRequestDto);

    @Mapping(target = "cards", source = "cards")
    UserResponseDto toDto(User user);

}
