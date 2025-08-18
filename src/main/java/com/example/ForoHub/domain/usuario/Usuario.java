package com.example.ForoHub.domain.usuario;

import com.example.ForoHub.domain.perfil.Perfil;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private String nombre;
    @Column(name="email", unique=true, nullable=false)
    private String email;
    private String contrasena;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="usuarios_perfiles",
            joinColumns=@JoinColumn(name="usuario_id"),
            inverseJoinColumns=@JoinColumn(name="perfil_id"))
    private Set<Perfil> perfiles;

    public Usuario(DatosRegistraUsuarioDTO datos){
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.contrasena = datos.contrasena();
        this.perfiles = datos.perfiles();
    }
}