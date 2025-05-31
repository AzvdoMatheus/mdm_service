package com.example.dem_service.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.dem_service.dtos.RawCountryDTO;
import com.example.dem_service.entities.Provider;
import com.example.dem_service.repository.ProviderRepository;

@Service
public class FetchService {

    private final ProviderRepository providerRepository;
    private final WebClient.Builder webClientBuilder;

    public FetchService(ProviderRepository providerRepository, WebClient.Builder webClientBuilder) {
        this.providerRepository = providerRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<RawCountryDTO> fetchFromAllProviders() {
        List<RawCountryDTO> allCountries = new ArrayList<>();
        List<Provider> providers = providerRepository.findAll();

        for (Provider provider : providers) {
            List<RawCountryDTO> countries = fetchCountriesFromProvider(provider);
            allCountries.addAll(countries);
        }
        return allCountries;
    }

    private List<RawCountryDTO> fetchCountriesFromProvider(Provider provider) {
        String baseUrl = provider.getEndpoint();
        WebClient client = webClientBuilder.baseUrl(baseUrl).build();

        try {
            return client.get()
                    .uri("/all")
                    .retrieve()
                    .bodyToFlux(RawCountryDTO.class)
                    .collectList()
                    .blockOptional()
                    .orElseGet(List::of);
        } catch (Exception e) {
            logError(baseUrl, e);
            return List.of();
        }
    }

    private void logError(String baseUrl, Exception e) {
        System.err.println("Failed to fetch from: " + baseUrl + "/all");
        e.printStackTrace();
    }
}
