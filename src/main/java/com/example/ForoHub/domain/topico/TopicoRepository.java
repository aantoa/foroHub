package com.example.ForoHub.domain.topico;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TopicoRepository extends JpaRepository <Topico, Long>{
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    Page<Topico> findByCurso_NombreIgnoreCaseAndFechaCreacionBetween(
            String nombreCurso, LocalDateTime inicio, LocalDateTime fin, Pageable pageable);
    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);
}
