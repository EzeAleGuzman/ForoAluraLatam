package com.alura.foro.controller;

import com.alura.foro.domain.usuario.RegistroUsuarioDTO;
import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.domain.usuario.UsuarioLoginDTO;
import com.alura.foro.domain.usuario.UsuarioRepository;
import com.alura.foro.infra.Segurity.DatosJWTToken;
import com.alura.foro.infra.Segurity.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Usuarios", description = "Esta capa se ocupa de la autenticacion y creacion de usuarios")
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuario", description = "Autentica un usuario y retorna un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(schema = @Schema(implementation = DatosJWTToken.class))),
            @ApiResponse(responseCode = "401", description = "Contraseña incorrecta"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error en el proceso de autenticación")
    })
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid UsuarioLoginDTO usuarioLoginDTO) {
        try {
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    usuarioLoginDTO.getNombre(),
                    usuarioLoginDTO.getContrasena()
            );
            var usuarioAutenticado = authenticationManager.authenticate(authToken);
            var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
            return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } catch (Exception e) {
            System.out.println("se metio en el error");
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el proceso de autenticación");
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid RegistroUsuarioDTO registroUsuarioDTO) {
        // Encriptar la contraseña
        String contrasenaEncriptada = passwordEncoder.encode(registroUsuarioDTO.getContrasena());

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroUsuarioDTO.getNombre());
        nuevoUsuario.setEmail(registroUsuarioDTO.getEmail());
        nuevoUsuario.setContrasena(contrasenaEncriptada);
        nuevoUsuario.setPerfil(registroUsuarioDTO.getPerfil());

        // Guardar el usuario en la base de datos
        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    };

}
