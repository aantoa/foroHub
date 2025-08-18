package com.example.ForoHub.domain.perfil;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="perfiles")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class Perfil implements GrantedAuthority {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @Override public String getAuthority() { return nombre; }
}
