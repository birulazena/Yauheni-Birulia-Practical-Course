package com.github.birulazena.user_service.mapper;

import com.github.birulazena.user_service.dto.CardInfoRequestDto;
import com.github.birulazena.user_service.dto.CardInfoResponseDto;
import com.github.birulazena.user_service.entities.CardInfo;
import com.github.birulazena.user_service.utils.CardInfoUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = CardInfoUtils.class)
public interface CardInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    CardInfo toEntity(CardInfoRequestDto cardInfoRequestDto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "hiddenNumber", expression = "java(CardInfoUtils.hideTheNumber(cardInfo.getNumber()))")
    CardInfoResponseDto toDto(CardInfo cardInfo);



}
