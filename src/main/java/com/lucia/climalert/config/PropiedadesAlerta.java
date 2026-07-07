package com.lucia.climalert.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "alerta")
public record PropiedadesAlerta(double umbralTemperaturaC, double umbralHumedadPorcentaje, List<String> destinatarios) {
}
