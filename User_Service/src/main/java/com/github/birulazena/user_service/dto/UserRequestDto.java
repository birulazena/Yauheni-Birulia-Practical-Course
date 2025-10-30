package com.github.birulazena.user_service.dto;

import com.github.birulazena.user_service.entities.CardInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

public record UserRequestDto(@NotBlank String name,
                             @NotBlank String surname,
                             @Past LocalDate birthDate,
                             @Email String email,
                             List<CardInfoRequestDto> cards ) {
}
