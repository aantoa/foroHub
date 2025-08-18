package com.example.ForoHub.domain.topico;

import com.example.ForoHub.domain.curso.Curso;
import com.example.ForoHub.domain.respuesta.Respuesta;
import com.example.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Topico {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Column(columnDefinition="TEXT")
    private String mensaje;
    @Column(name="fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
    private TopicoStatus status = TopicoStatus.ABIERTO;

    @ManyToOne @JoinColumn(name="autor_id")
    private Usuario autor;
    @ManyToOne @JoinColumn(name="curso_id")
    private Curso curso;

    @OneToMany(mappedBy="topico", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(DatosRegistroTopicoDTO datos, Usuario autor, Curso curso){
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.autor = autor;
        this.curso = curso;
    }

    @PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
    }

    public void actualizarTopico(@Valid DatosActualizarTopicoDTO datos, Curso nuevoCurso){
        if (datos.titulo() != null && !datos.titulo().isBlank()) {
           this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null && !datos.mensaje().isBlank()) {
            this.mensaje = datos.mensaje();
        }
        if (datos.status() != null) {
            this.status = datos.status();
        }
        if (nuevoCurso != null) {
            this.curso = nuevoCurso;
        }
    }
}
