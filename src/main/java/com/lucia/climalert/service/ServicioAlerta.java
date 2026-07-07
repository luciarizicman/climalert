package com.lucia.climalert.service;

import com.lucia.climalert.config.PropiedadesAlerta;
import com.lucia.climalert.entity.Alerta;
import com.lucia.climalert.entity.RegistroClima;
import com.lucia.climalert.repository.AlertaRepository;
import com.lucia.climalert.repository.RegistroClimaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class ServicioAlerta {

    private final RegistroClimaRepository registroClimaRepository;
    private final AlertaRepository alertaRepository;
    private final ServicioNotificacionCorreo servicioNotificacionCorreo;
    private final PropiedadesAlerta propiedadesAlerta;

    public ServicioAlerta(RegistroClimaRepository registroClimaRepository,
                           AlertaRepository alertaRepository,
                           ServicioNotificacionCorreo servicioNotificacionCorreo,
                           PropiedadesAlerta propiedadesAlerta) {
        this.registroClimaRepository = registroClimaRepository;
        this.alertaRepository = alertaRepository;
        this.servicioNotificacionCorreo = servicioNotificacionCorreo;
        this.propiedadesAlerta = propiedadesAlerta;
    }

    public void evaluarUltimoRegistro() {
        Optional<RegistroClima> ultimo = registroClimaRepository.findTopByOrderByObtenidoEnDesc();
        if (ultimo.isEmpty()) {
            log.debug("Aún no hay registros climáticos para analizar.");
            return;
        }

        RegistroClima registro = ultimo.get();
        if (!esCritico(registro)) {
            return;
        }

        if (alertaRepository.existsByRegistroClima(registro)) {
            log.debug("Ya se notificó una alerta para el registro {}.", registro.getId());
            return;
        }

        servicioNotificacionCorreo.enviarCorreoAlerta(registro);

        Alerta alerta = Alerta.builder()
                .registroClima(registro)
                .enviadaEn(LocalDateTime.now())
                .mensaje("Temperatura %.1f°C / Humedad %.1f%%".formatted(
                        registro.getTemperaturaCelsius(), registro.getHumedadPorcentaje()))
                .build();
        alertaRepository.save(alerta);
    }

    private boolean esCritico(RegistroClima registro) {
        return registro.getTemperaturaCelsius() > propiedadesAlerta.umbralTemperaturaC()
                && registro.getHumedadPorcentaje() > propiedadesAlerta.umbralHumedadPorcentaje();
    }
}
