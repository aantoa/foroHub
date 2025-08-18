package com.example.ForoHub.domain.perfil;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="perfiles")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class Perfil{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nombre;
}
