package com.example.dem_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dem_service.services.FetchService;
import com.example.dem_service.services.JsonNormalizationService;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Endpoint que dispara o fetch + normalização (Country + Currency).
 * Ele retorna a própria lista de JsonNode trazida pelo provider, mas você
 * pode alterar para devolver a lista de Country ou qualquer outra coisa.
 */
@RestController
public class CountryAPIController {

    private final FetchService fetchService;
    private final JsonNormalizationService normalizationService;

    public CountryAPIController(
        FetchService fetchService,
        JsonNormalizationService normalizationService
    ) {
        this.fetchService = fetchService;
        this.normalizationService = normalizationService;
    }

    @GetMapping("/fetch")
    public List<JsonNode> fetchAndNormalize() throws Exception {
        // 1) Baixa o JSON bruto de todos os providers:
        List<JsonNode> raw = fetchService.fetchFromAllProviders();

        // 2) Normaliza e salva País + Moeda no banco:
        if (!raw.isEmpty()) {
            normalizationService.normalizeAndSave(raw);
        }

        // 3) Retorna, a critério seu, o próprio JSON bruto ou
        //    até mesmo uma nova coleção de Country salvos.
        return raw;
    }
}
