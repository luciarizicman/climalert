package com.lucia.climalert.scheduler;

import com.lucia.climalert.service.ServicioIngestaClima;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProgramadorClima {

    private static final long CINCO_MINUTOS_MS = 5 * 60 * 1000;

    private final ServicioIngestaClima servicioIngestaClima;

    public ProgramadorClima(ServicioIngestaClima servicioIngestaClima) {
        this.servicioIngestaClima = servicioIngestaClima;
    }

    @Scheduled(fixedRate = CINCO_MINUTOS_MS)
    public void obtenerClimaActual() {
        try {
            servicioIngestaClima.obtenerYAlmacenar();
        } catch (Exception e) {
            log.error("Error al obtener/almacenar datos climáticos", e);
        }
    }
}
