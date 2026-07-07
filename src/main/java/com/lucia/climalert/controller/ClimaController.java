package com.lucia.climalert.controller;

import com.lucia.climalert.entity.RegistroClima;
import com.lucia.climalert.repository.RegistroClimaRepository;
import com.lucia.climalert.service.ServicioAlerta;
import com.lucia.climalert.service.ServicioIngestaClima;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clima")
public class ClimaController {

    private final RegistroClimaRepository registroClimaRepository;
    private final ServicioIngestaClima servicioIngestaClima;
    private final ServicioAlerta servicioAlerta;

    public ClimaController(RegistroClimaRepository registroClimaRepository,
                            ServicioIngestaClima servicioIngestaClima,
                            ServicioAlerta servicioAlerta) {
        this.registroClimaRepository = registroClimaRepository;
        this.servicioIngestaClima = servicioIngestaClima;
        this.servicioAlerta = servicioAlerta;
    }

    @GetMapping("/registros/ultimo")
    public ResponseEntity<RegistroClima> ultimoRegistro() {
        return registroClimaRepository.findTopByOrderByObtenidoEnDesc()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping("/registros")
    public RegistroClima crearRegistro() {
        return servicioIngestaClima.obtenerYAlmacenar();
    }

    @PostMapping("/alertas")
    public ResponseEntity<Void> crearAlerta() {
        servicioAlerta.evaluarUltimoRegistro();
        return ResponseEntity.ok().build();
    }
}
