package com.library.backend_epilogo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja las excepciones org.springframework.web.HttpRequestMethodNotSupportedException
    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupported(Exception ex) {
        return new ResponseEntity<>(
                "El método HTTP no está soportado para esta ruta.",
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    // Manejo adicional para rutas no encontradas (opcional)
    @RequestMapping(value = "**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<String> handleUnknownRoutes() {
        return new ResponseEntity<>(
                "La ruta que has solicitado no existe.",
                HttpStatus.NOT_FOUND
        );
    }
}