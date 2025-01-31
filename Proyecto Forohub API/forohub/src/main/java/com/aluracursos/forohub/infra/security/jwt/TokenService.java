package com.aluracursos.forohub.infra.security.jwt;

import com.aluracursos.forohub.domain.user.Usuario;
import com.aluracursos.forohub.infra.errors.JWTException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${spring.security.jwt.secret}")
    private String secretAPI;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretAPI);
            return JWT.create()
                    .withIssuer("forohub")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaDeExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Error al crear el Token JWT: ", exception);
        }
    }

    public String getSubject(String token) throws JWTException{
        if (token == null){
            throw new IllegalArgumentException("Token Vacío No Valido");
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretAPI);
            verifier = JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTDecodeException exception) {
            throw new JWTException("Formato de Token Inesperado. Por favor ingrese uno valido", exception.getCause());
        } catch (JWTVerificationException exception) {
            throw new JWTException("Error al verificar el token JWT", exception.getCause());
        }

        if(verifier.getSubject() == null){
            throw new JWTException("Token Invalidado Por Verificación");
        }
        return verifier.getSubject();
    }

    public boolean isTokenValid(String token) {
        boolean verificador = false;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretAPI);
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build()
                    .verify(token);
            verificador = verifier.getExpiresAt().after(new Date());
        } catch (JWTVerificationException exception) {
            throw new JWTException("Error al verificar durabilidad del token JWT");
        }
        if (!verificador) {
            throw new ValidationException("Token Vencido");
        }
        return verificador;
    }

    private Instant generarFechaDeExpiracion() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-05:00"));
    }



}
