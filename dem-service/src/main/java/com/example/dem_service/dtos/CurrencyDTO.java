package com.example.dem_service.dtos;

import java.time.LocalDateTime;

public record CurrencyDTO(
    Integer currencyId,
    String currencyCode,
    String currencyName,
    String currencySymbol,
    Integer countryId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) { }
