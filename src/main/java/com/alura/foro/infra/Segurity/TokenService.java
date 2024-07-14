package com.alura.foro.infra.Segurity;

import com.alura.foro.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            System.out.println("Creando token");
            String token = JWT.create()
                    .withIssuer("foro-alura")
                    .withSubject(usuario.getNombre())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
            System.out.println("Token generado: " + token);
            return token;
        } catch (JWTCreationException exception) {
            System.out.println("API Secret: " + apiSecret);
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new RuntimeException("Token no puede ser nulo o vacío");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // validando firma
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("foro-alura")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println("Error al verificar el token: " + exception.getMessage());
            System.out.println("Token: " + token);
            System.out.println("API Secret: " + apiSecret);
            throw new RuntimeException("Token JWT inválido", exception);
        }
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);
    }
}
