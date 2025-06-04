package com.example.mdm_service.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mdm_service.dtos.CountryDTO;
import com.example.mdm_service.entities.Country;
import com.example.mdm_service.repository.CountriesRepository;

@Service
public class CountryService {

    private final CountriesRepository countryRepository;

    public CountryService(CountriesRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Retorna todos os países no formato DTO.
     */
    @Transactional(readOnly = true)
    public List<CountryDTO> findAll() {
        return countryRepository.findAll()
                                .stream()
                                .map(CountryDTO::fromEntity)
                                .toList();
    }

    /**
     * Busca um país pelo nome e retorna como DTO.
     * Lança RuntimeException se não encontrado.
     */
    @Transactional(readOnly = true)
    public CountryDTO findByName(String countryName) {
        Country entity = countryRepository.findByCountryName(countryName)
                .orElseThrow(() -> new RuntimeException("Country '" + countryName + "' not found"));
        return CountryDTO.fromEntity(entity);
    }

    /**
     * Cria um novo Country a partir de CountryDTO. Retorna o DTO com ID gerado.
     * (Aqui assumimos que `dto.countryId()` vem zero ou ignorável.)
     */
    @Transactional
    public CountryDTO create(CountryDTO dto) {
        // 1) Construir uma instância de Country em branco
        Country entity = new Country();

        // 2) Preencher campos via updateEntity() do próprio DTO
        //    (inclui mapeamento de lista de CurrencyDTO → Currency)
        dto.updateEntity(entity);

        // 3) Salvar e devolver o DTO resultante
        Country saved = countryRepository.save(entity);
        return CountryDTO.fromEntity(saved);
    }

    /**
     * Atualiza um Country existente (buscando pelo nome antigo) usando os campos do DTO.
     * Retorna o DTO atualizado.
     */
    @Transactional
    public CountryDTO updateByName(String countryName, CountryDTO dto) {
        // 1) Buscar a entidade que já existe
        Country entity = countryRepository.findByCountryName(countryName)
                .orElseThrow(() -> new RuntimeException("Country '" + countryName + "' not found"));

        // 2) Atualizar todos os campos (inclusive lista de moedas) usando o método do DTO
        dto.updateEntity(entity);

        // 3) Salvar e retornar o DTO atualizado
        Country updated = countryRepository.save(entity);
        return CountryDTO.fromEntity(updated);
    }

    /**
     * Exclui um Country pelo nome. Se não existir, lança RuntimeException.
     */
    @Transactional
    public void deleteByName(String countryName) {
        Country entity = countryRepository.findByCountryName(countryName)
                .orElseThrow(() -> new RuntimeException("Country '" + countryName + "' not found"));
        countryRepository.delete(entity);
    }
}
