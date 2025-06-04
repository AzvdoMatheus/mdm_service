package com.example.mdm_service.dtos;

import com.example.mdm_service.entities.Country;
import com.example.mdm_service.entities.Currency;

public record CurrencyDTO(
    int currencyId,
    String currencyCode,
    String currencyName,
    String currencySymbol
) {
    public Currency toEntity(Country country) {
        Currency c = new Currency();
        c.setCurrencyCode(this.currencyCode());
        c.setCurrencyName(this.currencyName());
        c.setCurrencySymbol(this.currencySymbol());
        c.setCountry(country);
        return c;
    }

    public static CurrencyDTO fromEntity(Currency c) {
        return new CurrencyDTO(
            c.getCurrencyId(),
            c.getCurrencyCode(),
            c.getCurrencyName(),
            c.getCurrencySymbol()
        );
    }
}
