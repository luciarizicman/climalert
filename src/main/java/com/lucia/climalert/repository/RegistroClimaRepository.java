package com.lucia.climalert.repository;

import com.lucia.climalert.entity.RegistroClima;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistroClimaRepository extends JpaRepository<RegistroClima, Long> {

    Optional<RegistroClima> findTopByOrderByObtenidoEnDesc();
}
