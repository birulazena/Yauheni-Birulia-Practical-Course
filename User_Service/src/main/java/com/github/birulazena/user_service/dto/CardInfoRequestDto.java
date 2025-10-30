package com.github.birulazena.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CardInfoRequestDto(Long userId,
                                 @NotBlank String number,
                                 @NotBlank String holder,
                                 LocalDate expirationDate) {
}
