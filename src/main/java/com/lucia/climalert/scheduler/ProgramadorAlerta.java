package com.lucia.climalert.scheduler;

import com.lucia.climalert.service.ServicioAlerta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProgramadorAlerta {

    private static final long UN_MINUTO_MS = 60 * 1000;

    private final ServicioAlerta servicioAlerta;

    public ProgramadorAlerta(ServicioAlerta servicioAlerta) {
        this.servicioAlerta = servicioAlerta;
    }

    @Scheduled(fixedRate = UN_MINUTO_MS)
    public void evaluarClima() {
        try {
            servicioAlerta.evaluarUltimoRegistro();
        } catch (Exception e) {
            log.error("Error al analizar condiciones climáticas", e);
        }
    }
}
