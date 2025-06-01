package com.example.dem_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.dem_service.entities.Currency;
import java.util.Optional;
import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    List<Currency> findByCountryId(Integer countryId);
    Optional<Currency> findByCurrencyCode(String currencyCode);
}
