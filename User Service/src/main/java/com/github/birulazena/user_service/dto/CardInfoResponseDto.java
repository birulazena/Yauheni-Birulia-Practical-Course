package com.github.birulazena.user_service.dto;

import java.time.LocalDate;

public record CardInfoResponseDto(Long id,
                                  Long userId,
                                  String hiddenNumber,
                                  String holder,
                                  LocalDate expirationDate) {
}
