package com.github.birulazena.user_service.mapper;

import com.github.birulazena.user_service.dto.CardInfoRequestDto;
import com.github.birulazena.user_service.dto.CardInfoResponseDto;
import com.github.birulazena.user_service.entities.CardInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    CardInfo toEntity(CardInfoRequestDto cardInfoRequestDto);

    @Mapper()
    CardInfoResponseDto toDto(CardInfo cardInfo);

    private String hideTheNumber(String number) {
        if (number == null || number.length() <= 4) {
            return number; // или вернуть "****"
        }
        return "*".repeat(number.length() - 4) + number.substring(number.length() - 4);
    }

}
