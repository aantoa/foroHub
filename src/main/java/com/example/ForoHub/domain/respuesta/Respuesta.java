package com.example.ForoHub.domain.respuesta;

import com.example.ForoHub.domain.topico.Topico;
import com.example.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="respuestas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of="id")
public class Respuesta {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT")
    private String mensaje;
    @Column(name="fecha_creacion")
    private LocalDateTime fechaCreacion;
    private boolean solucion;

    @ManyToOne @JoinColumn(name="autor_id") private Usuario autor;
    @ManyToOne @JoinColumn(name="topico_id") private Topico topico;
}
