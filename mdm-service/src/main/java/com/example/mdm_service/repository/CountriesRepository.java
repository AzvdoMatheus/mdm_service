package com.example.mdm_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mdm_service.entities.Country;

@Repository
public interface CountriesRepository extends JpaRepository<Country, Integer> {
  Optional<Country> findByCountryId(int countryId);
  Optional<Country> findByCountryName(String countryName);
}
