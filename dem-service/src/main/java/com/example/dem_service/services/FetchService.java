package com.example.dem_service.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import com.example.dem_service.entities.Provider;
import com.example.dem_service.repository.ProviderRepository;

@Service
public class FetchService {

    private final ProviderRepository providerRepository;
    private final WebClient.Builder webClientBuilder;
    private final DownloadsService downloadsService; // assume você tem esse serviço

    public FetchService(
        ProviderRepository providerRepository,
        WebClient.Builder webClientBuilder,
        DownloadsService downloadsService
    ) {
        this.providerRepository = providerRepository;
        this.webClientBuilder = webClientBuilder;
        this.downloadsService = downloadsService;
    }

    /**
     * Baixa o JSON de todos os providers, salva o arquivo bruto em disco 
     * e retorna AINDA o List<JsonNode> para quem precisar normalizar depois.
     */
    public List<JsonNode> fetchFromAllProviders() {
        List<JsonNode> allCountries = new ArrayList<>();
        List<Provider> providers = providerRepository.findAll();

        for (Provider provider : providers) {
            // 1) Baixa a lista crua de países como JsonNode
            List<JsonNode> countries = fetchJsonFromProvider(provider);
            if (!countries.isEmpty()) {
                allCountries.addAll(countries);
                // 2) Salva o JSON bruto na pasta “downloads” e registra no BD
                downloadsService.saveRawJsonAndRecord(provider, countries);
            }
        }
        return allCountries;
    }

    private List<JsonNode> fetchJsonFromProvider(Provider provider) {
        String baseUrl = provider.getEndpoint();
        WebClient client = webClientBuilder.baseUrl(baseUrl).build();
        try {
            return client.get()
                         .uri("/all")
                         .retrieve()
                         .bodyToFlux(JsonNode.class)
                         .collectList()
                         .blockOptional()
                         .orElseGet(List::of);
        } catch (Exception e) {
            System.err.println("Failed to fetch from: " + baseUrl + "/all");
            return List.of();
        }
    }
}
