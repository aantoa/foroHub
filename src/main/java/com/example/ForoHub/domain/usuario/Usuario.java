package com.example.ForoHub.domain.usuario;

import com.example.ForoHub.domain.perfil.Perfil;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {
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

    // UserDetails
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return perfiles; }
    @Override public String getPassword() { return contrasena; }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}