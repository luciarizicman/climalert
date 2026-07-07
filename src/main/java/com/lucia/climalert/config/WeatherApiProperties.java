package com.lucia.climalert.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weatherapi")
public record WeatherApiProperties(String urlBase, String claveApi, String ubicacion) {
}
