package com.example.dem_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dem_service.dtos.CountryDTO;
import com.example.dem_service.services.FetchService;
import com.example.dem_service.services.NormalizeJsonService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class CountryAPIController {

    private final FetchService fetchService;
    private final NormalizeJsonService normalizeJsonService;

    // Spring injetará ambos: FetchService e NormalizeJsonService
    public CountryAPIController(FetchService fetchService,
                                NormalizeJsonService normalizeJsonService) {
        this.fetchService = fetchService;
        this.normalizeJsonService = normalizeJsonService;
    }

    @GetMapping("/fetch")
    public List<CountryDTO> fetchAndNormalize() throws Exception {
        // 1) Busca o JSON bruto de todos os providers (REST Countries, por exemplo)
        List<JsonNode> rawCountries = fetchService.fetchFromAllProviders();

        // 2) Normaliza cada JsonNode para CountryDTO via NormalizeJsonService
        List<CountryDTO> normalized = normalizeJsonService.normalizeCountries(rawCountries);

        // 3) Retorna a lista de CountryDTO para o cliente
        return normalized;
    }
}
