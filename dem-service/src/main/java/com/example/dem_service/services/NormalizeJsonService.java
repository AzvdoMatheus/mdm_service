package com.example.dem_service.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;  // <-- importe @Service

import com.example.dem_service.dtos.CountryDTO;
import com.example.dem_service.dtos.CurrencyDTO;
import com.fasterxml.jackson.databind.JsonNode;

@Service   // <-- o Spring passará a enxergar esta classe como bean
public class NormalizeJsonService {

    public List<CountryDTO> normalizeCountries(List<JsonNode> rawJsonNodes) {
        List<CountryDTO> normalizedList = new ArrayList<>();

        for (JsonNode node : rawJsonNodes) {
            // countryId placeholder (será gerado no BD, por exemplo)
            int countryId = 0;

            // 1) Nome do país: node.name.common
            String countryName = node.path("name").path("common").asText("");

            // 2) Código de três letras (para countryCode): node.cca3
            String countryCode = node.path("cca3").asText("");

            // 3) Capital: primeiro elemento de node.capital[]
            String capitalCity = "";
            if (node.has("capital") && node.path("capital").isArray() && node.path("capital").size() > 0) {
                capitalCity = node.path("capital").get(0).asText("");
            }

            // 4) Código numérico: node.ccn3 (string) convertido para int
            int numericCode = 0;
            if (node.has("ccn3") && !node.path("ccn3").isNull()) {
                try {
                    numericCode = Integer.parseInt(node.path("ccn3").asText("0"));
                } catch (NumberFormatException e) {
                    numericCode = 0;
                }
            }

            // 5) Population
            int population = node.path("population").asInt(0);

            // 6) Area (float)
            float area = (float) node.path("area").asDouble(0.0);

            // 7) Continente: primeiro elemento de node.continents[]
            String continent = "";
            if (node.has("continents") && node.path("continents").isArray() && node.path("continents").size() > 0) {
                continent = node.path("continents").get(0).asText("");
            }

            // 8) createdAt / updatedAt: uso LocalDateTime.now() como exemplo
            LocalDateTime createdAt = LocalDateTime.now();
            LocalDateTime updatedAt = LocalDateTime.now();

            // 9) Montar lista de CurrencyDTO
            List<CurrencyDTO> currencies = new ArrayList<>();
            JsonNode currenciesNode = node.path("currencies");
            if (currenciesNode.isObject()) {
                // Iterar sobre cada código de moeda (chave dinâmica) -> { name, symbol }
                Iterator<Map.Entry<String, JsonNode>> fields = currenciesNode.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    String code = entry.getKey(); // ex: "MNT"
                    JsonNode info = entry.getValue();
                    String currencyName = info.path("name").asText("");
                    String currencySymbol = info.path("symbol").asText("");

                    // currencyId placeholder = 0
                    CurrencyDTO currencyDTO = new CurrencyDTO(
                        0,               // currencyId
                        code,            // currencyCode (ex: "MNT")
                        currencyName,    // currencyName (ex: "Mongolian tögrög")
                        currencySymbol   // currencySymbol (ex: "₮")
                    );
                    currencies.add(currencyDTO);
                }
            }

            // 10) Construir CountryDTO
            CountryDTO dto = new CountryDTO(
                countryId,       // int countryId
                countryName,     // String countryName
                countryCode,     // String countryCode
                capitalCity,     // String capitalCity
                numericCode,     // int numericCode
                population,      // int population
                area,            // float area
                continent,       // String continent
                createdAt,       // LocalDateTime createdAt
                updatedAt,       // LocalDateTime updatedAt
                currencies       // List<CurrencyDTO> currencies
            );

            normalizedList.add(dto);
        }

        return normalizedList;
    }
}
