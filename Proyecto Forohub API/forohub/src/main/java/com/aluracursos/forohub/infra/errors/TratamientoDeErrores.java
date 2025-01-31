package com.aluracursos.forohub.infra.errors;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class TratamientoDeErrores {

    @ExceptionHandler(JWTException.class)
    public ResponseEntity tratamientoErroresJWT(JWTException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratamientoError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ExcepcionDeValidacionEnCreacion.class)
    public ResponseEntity tratamientoErrorDeValidacionEnCreacion(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratamientoError400(MethodArgumentNotValidException error) {
        var errores = error.getFieldErrors().stream()
                .map(DatosDeErrorValidacion::new)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity tratamientoErrorDeValidacion(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity tratamientoErrorDeAutenticacion(AuthenticationException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity tratamientoErrorNullPointer(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity tratamientoErrorIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity tratamientoErrorJWTCreation(JWTCreationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity tratamientoErrorJWTDecodeException(JWTDecodeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    public record DatosDeErrorValidacion(String campoErroneo, String error) {
        public DatosDeErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}