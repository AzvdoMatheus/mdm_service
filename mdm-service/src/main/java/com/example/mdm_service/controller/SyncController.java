package com.example.mdm_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mdm_service.client.DemServiceClient;
import com.example.mdm_service.dtos.CountryDTO;
import com.example.mdm_service.entities.Country;
import com.example.mdm_service.services.StoreCountriesService;

@RestController
public class SyncController {

    private final DemServiceClient demServiceClient;
    private final StoreCountriesService storeCountriesService;

    public SyncController(DemServiceClient demServiceClient,
                          StoreCountriesService storeCountriesService) {
        this.demServiceClient = demServiceClient;
        this.storeCountriesService = storeCountriesService;
    }

    @GetMapping("/sync")
    public ResponseEntity<List<CountryDTO>> syncCountries() {
        List<CountryDTO> dtosFromDem = demServiceClient.receiveDataFromDEM();

        List<Country> persisted = storeCountriesService.saveAllCountries(dtosFromDem);

        List<CountryDTO> response = persisted.stream()
            .map(CountryDTO::fromEntity)
            .toList();

        return ResponseEntity.ok(response);
    }
}
