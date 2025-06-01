package com.example.dem_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "download")
@Getter
@Setter
public class Download {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id", nullable = false)
    private Provider provider;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "filepath", nullable = false)
    private String filepath;

    public Download() {}

    public Download(Provider provider, LocalDateTime date, String filepath) {
        this.provider = provider;
        this.date = date;
        this.filepath = filepath;
    }
}
