package com.lucia.climalert.repository;

import com.lucia.climalert.entity.Alerta;
import com.lucia.climalert.entity.RegistroClima;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    boolean existsByRegistroClima(RegistroClima registroClima);
}
