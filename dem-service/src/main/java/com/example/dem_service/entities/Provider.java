package com.example.dem_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "provider",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
@Getter
@Setter
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String endpoint;

    public Provider() {}

    public Provider(String name, String endpoint) {
        this.name = name;
        this.endpoint = endpoint;
    }
}
