package com.example.ForoHub.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity gestionarerror404(EntityNotFoundException ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body(HttpStatus.NOT_FOUND, ex.getMessage(), req));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity gestionarError400(MethodArgumentNotValidException ex, WebRequest req) {
        List<DatosErrorValidacion> errores = ex.getFieldErrors()
                .stream().map(DatosErrorValidacion::new).toList();

        Map<String, Object> mapa = body(HttpStatus.BAD_REQUEST, "Errores de validación", req);
        mapa.put("errors", errores);
        return ResponseEntity.badRequest().body(mapa);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<?> gestionarError422(DuplicateResourceException ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(body(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), req));
    }

    private Map<String, Object> body(HttpStatus status, String message, WebRequest req) {
        String uri = req.getDescription(false).replace("uri=", "");
        Map<String, Object> map = new HashMap<>();
        map.put("status", status.value());
        map.put("error", status.getReasonPhrase());
        map.put("message", message);
        map.put("path", uri);
        return map;
    }

    public record DatosErrorValidacion(String campo, String mensaje){
        public DatosErrorValidacion(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
