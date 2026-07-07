package com.lucia.climalert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroClima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreUbicacion;

    @Column(nullable = false)
    private LocalDateTime observadoEn;

    @Column(nullable = false)
    private LocalDateTime obtenidoEn;

    @Column(nullable = false)
    private Double temperaturaCelsius;

    @Column(nullable = false)
    private Double humedadPorcentaje;

    private String condicionTexto;

    private Double vientoKph;

    private Double sensacionTermicaCelsius;
}
