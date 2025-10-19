package com.github.birulazena.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

public record UserResponseDto(Long id,
                              String name,
                              String surname,
                              LocalDate birthDate,
                              String email,
                              List<CardInfoResponseDto> cards) {
}
