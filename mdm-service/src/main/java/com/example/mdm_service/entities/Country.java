package com.example.mdm_service.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;

    @Column(nullable = false)
    private String countryName;

    @Column(nullable = false)
    private String countryCode;

    @Column(nullable = false)
    private String capitalCity;

    @Column(nullable = false)
    private int numericCode;

    @Column(nullable = false)
    private int population;

    @Column(nullable = false)
    private float area;

    @Column(nullable = false)
    private String continent;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(
        mappedBy = "country",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<Currency> currencies;

    public Country() {}

    public Country(
        String countryCode,
        String countryName,
        int numericCode,
        String capitalCity,
        int population,
        float area,
        String continent,
        List<Currency> currencies,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.numericCode = numericCode;
        this.capitalCity = capitalCity;
        this.population = population;
        this.area = area;
        this.continent = continent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.currencies = currencies;
    }
}
