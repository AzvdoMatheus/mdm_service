package com.example.dem_service.dtos;

public record CurrencyDTO(
    int currencyId,
    String currencyCode,
    String currencyName,
    String currencySymbol
) {
}
