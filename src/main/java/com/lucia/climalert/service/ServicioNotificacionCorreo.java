package com.lucia.climalert.service;

import com.lucia.climalert.config.PropiedadesAlerta;
import com.lucia.climalert.entity.RegistroClima;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ServicioNotificacionCorreo {

    private static final DateTimeFormatter FORMATO_VISUALIZACION = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JavaMailSender mailSender;
    private final PropiedadesAlerta propiedadesAlerta;

    public ServicioNotificacionCorreo(JavaMailSender mailSender, PropiedadesAlerta propiedadesAlerta) {
        this.mailSender = mailSender;
        this.propiedadesAlerta = propiedadesAlerta;
    }

    public void enviarCorreoAlerta(RegistroClima registro) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(propiedadesAlerta.destinatarios().toArray(new String[0]));
        mensaje.setSubject("[Climalert] Alerta meteorológica en " + registro.getNombreUbicacion());
        mensaje.setText(construirCuerpo(registro));

        mailSender.send(mensaje);
        log.info("Correo de alerta enviado a {}", propiedadesAlerta.destinatarios());
    }

    private String construirCuerpo(RegistroClima registro) {
        return """
                Se detectaron condiciones climáticas peligrosas en %s.

                Detalle del clima:
                - Temperatura: %.1f °C (sensación térmica: %.1f °C)
                - Humedad: %.1f %%
                - Condición: %s
                - Viento: %.1f km/h
                - Observado: %s
                - Registrado: %s

                Umbrales de alerta configurados:
                - Temperatura > %.1f °C
                - Humedad > %.1f %%

                Este es un mensaje automático generado por Climalert.
                """.formatted(
                registro.getNombreUbicacion(),
                registro.getTemperaturaCelsius(),
                registro.getSensacionTermicaCelsius(),
                registro.getHumedadPorcentaje(),
                registro.getCondicionTexto(),
                registro.getVientoKph(),
                registro.getObservadoEn().format(FORMATO_VISUALIZACION),
                registro.getObtenidoEn().format(FORMATO_VISUALIZACION),
                propiedadesAlerta.umbralTemperaturaC(),
                propiedadesAlerta.umbralHumedadPorcentaje()
        );
    }
}
