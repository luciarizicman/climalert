package com.lucia.climalert.service;

import com.lucia.climalert.client.WeatherApiClient;
import com.lucia.climalert.client.dto.WeatherApiResponse;
import com.lucia.climalert.entity.RegistroClima;
import com.lucia.climalert.repository.RegistroClimaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ServicioIngestaClima {

    private static final DateTimeFormatter FORMATO_FECHA_WEATHER_API =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final WeatherApiClient weatherApiClient;
    private final RegistroClimaRepository registroClimaRepository;

    public ServicioIngestaClima(WeatherApiClient weatherApiClient,
                                 RegistroClimaRepository registroClimaRepository) {
        this.weatherApiClient = weatherApiClient;
        this.registroClimaRepository = registroClimaRepository;
    }

    public RegistroClima obtenerYAlmacenar() {
        WeatherApiResponse respuesta = weatherApiClient.obtenerClimaActual();

        RegistroClima registro = RegistroClima.builder()
                .nombreUbicacion(respuesta.location().nombre())
                .observadoEn(LocalDateTime.parse(respuesta.actual().ultimaActualizacion(), FORMATO_FECHA_WEATHER_API))
                .obtenidoEn(LocalDateTime.now())
                .temperaturaCelsius(respuesta.actual().temperaturaC())
                .humedadPorcentaje(respuesta.actual().humedad())
                .condicionTexto(respuesta.actual().condicion() != null ? respuesta.actual().condicion().texto() : null)
                .vientoKph(respuesta.actual().vientoKph())
                .sensacionTermicaCelsius(respuesta.actual().sensacionTermicaC())
                .build();

        RegistroClima guardado = registroClimaRepository.save(registro);
        log.info("Registro climático almacenado: {}°C, {}% humedad ({})",
                guardado.getTemperaturaCelsius(), guardado.getHumedadPorcentaje(), guardado.getNombreUbicacion());
        return guardado;
    }
}
