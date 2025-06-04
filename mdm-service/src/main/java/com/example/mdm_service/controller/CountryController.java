package com.example.mdm_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.mdm_service.dtos.CountryDTO;
import com.example.mdm_service.services.CountryService;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * GET /countries
     * Retorna a lista de todos os países.
     */
    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAll() {
        List<CountryDTO> todos = countryService.findAll();
        return ResponseEntity.ok(todos);
    }

    /**
     * GET /countries/{name}
     * Retorna um único país pelo nome.
     */
    @GetMapping("/{name}")
    public ResponseEntity<CountryDTO> getByName(@PathVariable("name") String countryName) {
        CountryDTO dto = countryService.findByName(countryName);
        return ResponseEntity.ok(dto);
    }

    /**
     * POST /countries
     * Cria um novo país. O corpo da requisição deve ser um CountryDTO sem countryId
     * (ou com countryId = 0). Retorna o CountryDTO recém-criado (com ID gerado).
     */
    @PostMapping
    public ResponseEntity<CountryDTO> create(@RequestBody CountryDTO dto) {
        CountryDTO criado = countryService.create(dto);
        return ResponseEntity.ok(criado);
    }

    /**
     * PUT /countries/{name}
     * Atualiza o país de nome {name} com os dados do CountryDTO enviado no corpo.
     * Retorna o CountryDTO atualizado.
     */
    @PutMapping("/{name}")
    public ResponseEntity<CountryDTO> update(
            @PathVariable("name") String countryName,
            @RequestBody CountryDTO dto) {
        CountryDTO atualizado = countryService.updateByName(countryName, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /countries/{name}
     * Remove o país de nome {name}, retornando 204 No Content em caso de sucesso.
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable("name") String countryName) {
        countryService.deleteByName(countryName);
        return ResponseEntity.noContent().build();
    }
}
