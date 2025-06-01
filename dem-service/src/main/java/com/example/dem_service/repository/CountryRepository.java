package com.example.dem_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.dem_service.entities.Country;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByCountryCode(String countryCode);
}
