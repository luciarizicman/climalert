package com.lucia.climalert.client;

import com.lucia.climalert.client.dto.WeatherApiResponse;
import com.lucia.climalert.config.WeatherApiProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class WeatherApiClient {

    private final RestClient restClient;
    private final WeatherApiProperties propiedades;

    public WeatherApiClient(RestClient weatherApiRestClient, WeatherApiProperties propiedades) {
        this.restClient = weatherApiRestClient;
        this.propiedades = propiedades;
    }

    public WeatherApiResponse obtenerClimaActual() {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("key", propiedades.claveApi())
                        .queryParam("q", propiedades.ubicacion())
                        .build())
                .retrieve()
                .body(WeatherApiResponse.class);
    }
}
