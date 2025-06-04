package com.example.mdm_service.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.example.mdm_service.entities.Country;
import com.example.mdm_service.entities.Currency;

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
    public void updateEntity(Country entity) {
        entity.setCountryName(this.countryName());
        entity.setCountryCode(this.countryCode());
        entity.setCapitalCity(this.capitalCity());
        entity.setNumericCode(this.numericCode());
        entity.setPopulation(this.population());
        entity.setArea(this.area());
        entity.setContinent(this.continent());
        entity.setCreatedAt(this.createdAt());
        entity.setUpdatedAt(this.updatedAt());

        List<Currency> currencyEntity = this.currencies().stream()
            .map(curDto -> curDto.toEntity(entity))
            .toList();

        entity.setCurrencies(currencyEntity);
    }

    public static CountryDTO fromEntity(Country c) {
        var currencies = c.getCurrencies().stream()
            .map(CurrencyDTO::fromEntity)
            .toList();

        return new CountryDTO(
            c.getCountryId(),
            c.getCountryName(),
            c.getCountryCode(),
            c.getCapitalCity(),
            c.getNumericCode(),
            c.getPopulation(),
            c.getArea(),
            c.getContinent(),
            c.getCreatedAt(),
            c.getUpdatedAt(),
            currencies
        );
    }
}
