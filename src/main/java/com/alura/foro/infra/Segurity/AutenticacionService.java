package com.alura.foro.infra.Segurity;

import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Usuario usuario = usuarioRepository.findByNombre(username);

            if (usuario == null) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
            return usuario;
        }

        private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
            return List.of(new SimpleGrantedAuthority(usuario.getPerfil().name()));

    }
}
