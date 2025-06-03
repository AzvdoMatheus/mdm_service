package com.example.dem_service.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record CountryDTO(
    int countryId,
    String countryName,
    String countryCode,
    String capitalCity,
    int numericCode,
    int population,
    float area,
    String continent,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<CurrencyDTO> currencies
) {
}
