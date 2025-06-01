// Currency.java
package com.example.dem_service.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer currencyId;

    @Column(nullable = false, unique = true)
    private String currencyCode;   

    @Column(nullable = false)
    private String currencyName;  

    @Column(nullable = false)
    private String currencySymbol; 

    @Column(nullable = false)
    private Integer countryId;     

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Currency() {}

    public Currency(
        String currencyCode,
        String currencyName,
        String currencySymbol,
        Integer countryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.countryId = countryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
