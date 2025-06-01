package com.example.dem_service.services;

import java.util.*;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.dem_service.dtos.CountryDTO;
import com.example.dem_service.dtos.CurrencyDTO;
import com.example.dem_service.entities.Country;
import com.example.dem_service.entities.Currency;
import com.example.dem_service.repository.CountryRepository;
import com.example.dem_service.repository.CurrencyRepository;

@Service
public class JsonNormalizationService {

    private final ObjectMapper objectMapper;
    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;

    public JsonNormalizationService(
        ObjectMapper objectMapper,
        CountryRepository countryRepository,
        CurrencyRepository currencyRepository
    ) {
        this.objectMapper = objectMapper;
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
    }

    /**
     * Para cada JsonNode bruto:
     *   - Desserializa em CountryDTO
     *   - Se o countryCode já existir, atualiza apenas updatedAt (e campos mutáveis, se quiser)
     *   - Se não existir, insere um novo Country
     * 
     * Em seguida:
     *   - Desserializa em CurrencyDTO, explode para cada moeda
     *   - Se a currencyCode já existir, faz update do updatedAt (e campos mutáveis)
     *   - Se não existir, insere novo Currency
     * 
     * Tudo dentro de uma única transação para evitar “meio caminho” em caso de erro.
     */
    @Transactional
    public List<Country> normalizeAndSave(List<JsonNode> rawNodes) throws Exception {
        List<Country> savedCountries = new ArrayList<>();

        // Primeiro, processar cada país
        for (JsonNode raw : rawNodes) {
            CountryDTO dto = objectMapper.treeToValue(raw, CountryDTO.class);

            // Extrair o código único do país (cca3) diretamente do JsonNode:
            String countryCode = raw.path("cca3").asText();

            // Buscar se já existe este país no banco:
            Optional<Country> maybeExisting = countryRepository.findByCountryCode(countryCode);
            Country countryEntity;
            if (maybeExisting.isPresent()) {
                // País já existe: atualizar apenas timestamp (ou outros campos, se você quiser)
                countryEntity = maybeExisting.get();
                countryEntity.setUpdatedAt(LocalDateTime.now());
                // Se quiser atualizar outros campos, faça aqui, ex:
                // countryEntity.setPopulation(dto.population());
                // countryEntity.setArea(dto.area());
                countryRepository.save(countryEntity);
            } else {
                // País não existe: criar novo
                // Extrair o continente do JsonNode:
                String continent = "";
                JsonNode continentsNode = raw.path("continents");
                if (continentsNode.isArray() && continentsNode.size() > 0) {
                    continent = continentsNode.get(0).asText();
                }

                Country newCountry = new Country(
                    countryCode,                  // (CCA3)
                    dto.countryName(),            // common name
                    dto.numericCode(),            // ccn3
                    dto.capitalCity(),            // capital[0]
                    dto.population(),             // population
                    dto.area(),                   // area
                    continent,                    // continente
                    dto.createdAt(),              // createdAt = now()
                    dto.updatedAt()               // updatedAt = now()
                );
                countryEntity = countryRepository.save(newCountry);
            }

            savedCountries.add(countryEntity);
        }

        // Agora, para cada JsonNode, processar as moedas
        for (JsonNode raw : rawNodes) {
            String countryCode = raw.path("cca3").asText();
            // Determinar countryId gerado/atualizado:
            Integer countryId = countryRepository.findByCountryCode(countryCode)
                                  .map(Country::getCountryId)
                                  .orElse(null);
            if (countryId == null) {
                // Isto não deve acontecer, pois acabamos de salvar/atualizar todos os países acima
                continue;
            }

            // Desserializar CurrencyDTO “pai” (contendo o Map<String,Csv>):
            CurrencyDTO baseDto = objectMapper.treeToValue(raw, CurrencyDTO.class);

            // Explodir para _uma instância_ de CurrencyDTO por cada moeda presente:
            List<CurrencyDTO> moedasDTOs = CurrencyDTO.explodeCurrencies(
                countryCode, 
                baseDto.currenciesMap()
            );

            // Para cada CurrencyDTO “flat”, insere ou atualiza:
            for (CurrencyDTO dto : moedasDTOs) {
                String currencyCode = dto.currencyCode();

                // Verificar se já existe essa moeda no banco:
                Optional<Currency> maybeMoedaExistente = currencyRepository.findByCurrencyCode(currencyCode);

                if (maybeMoedaExistente.isPresent()) {
                    // Já existe essa moeda: apenas atualizar updatedAt (ou outros campos, se quiser)
                    Currency existingCurrency = maybeMoedaExistente.get();
                    existingCurrency.setUpdatedAt(LocalDateTime.now());
                    // Se quiser atualizar nome/símbolo, descomente abaixo:
                    // existingCurrency.setCurrencyName(dto.currencyName());
                    // existingCurrency.setCurrencySymbol(dto.currencySymbol());
                    currencyRepository.save(existingCurrency);
                } else {
                    // Não existe: criar nova
                    Currency newCurrency = new Currency(
                        dto.currencyCode(),
                        dto.currencyName(),
                        dto.currencySymbol(),
                        countryId,
                        dto.createdAt(),
                        dto.updatedAt()
                    );
                    currencyRepository.save(newCurrency);
                }
            }
        }

        return savedCountries;
    }
}
