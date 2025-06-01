package com.example.dem_service.dtos;

/**
 * Este record representa exatamente cada moeda a ser devolvida dentro de "currencies":
 * 
 * {
 *   "currencyId": 35,
 *   "currencyCode": "PAB",
 *   "currencyName": "Panamanian balboa",
 *   "currencySymbol": "B/."
 * }
 */
public record CurrencyDTO(
    int currencyId,
    String currencyCode,
    String currencyName,
    String currencySymbol
) {
}
