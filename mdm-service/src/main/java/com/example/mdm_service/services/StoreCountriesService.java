package com.example.mdm_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mdm_service.dtos.CountryDTO;
import com.example.mdm_service.entities.Country;
import com.example.mdm_service.repository.CountriesRepository;

@Service
public class StoreCountriesService {

    private final CountriesRepository countryRepository;

    public StoreCountriesService(CountriesRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Transactional
    public List<Country> saveAllCountries(List<CountryDTO> countryDTOs) {
        return countryDTOs.stream()
            .map(dto -> {
                Country country;
                if (dto.countryId() != 0) {
                    Optional<Country> opt = countryRepository.findByCountryId(dto.countryId());
                    country = opt.orElse(new Country());
                } else {
                    country = new Country();
                }

                dto.updateEntity(country);

                return countryRepository.save(country);
            })
            .toList();
    }
}
