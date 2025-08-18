package com.example.ForoHub.domain.curso;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="cursos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of="id")
public class Curso {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;
}
