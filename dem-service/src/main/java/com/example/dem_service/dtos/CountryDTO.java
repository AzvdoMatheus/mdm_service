package com.example.dem_service.dtos;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Este record agora representa exatamente a saída JSON que queremos devolver ao usuário:
 *
 * {
 *   "countryId": 2,
 *   "countryName": "Panama",
 *   "countryCode": "PAN",
 *   "capitalCity": "Panama City",
 *   "numericCode": 591,
 *   "population": 4314768,
 *   "area": 75417.0,
 *   "continent": "North America",
 *   "createdAt": "2025-06-01T23:18:36.620582",
 *   "updatedAt": "2025-06-01T23:25:27.139831677",
 *   "currencies": [
 *     {
 *       "currencyId": 35,
 *       "currencyCode": "PAB",
 *       "currencyName": "Panamanian balboa",
 *       "currencySymbol": "B/."
 *     },
 *     {
 *       "currencyId": 36,
 *       "currencyCode": "USD",
 *       "currencyName": "United States dollar",
 *       "currencySymbol": "$"
 *     }
 *   ]
 * }
 */
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
