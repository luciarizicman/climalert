package com.lucia.climalert.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherApiResponse(Ubicacion location, @JsonProperty("current") Actual actual) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Ubicacion(@JsonProperty("name") String nombre, String region, String country, String localtime) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Actual(
            @JsonProperty("last_updated") String ultimaActualizacion,
            @JsonProperty("temp_c") Double temperaturaC,
            @JsonProperty("feelslike_c") Double sensacionTermicaC,
            @JsonProperty("humidity") Double humedad,
            @JsonProperty("wind_kph") Double vientoKph,
            @JsonProperty("condition") Condicion condicion
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Condicion(@JsonProperty("text") String texto) {
    }
}
