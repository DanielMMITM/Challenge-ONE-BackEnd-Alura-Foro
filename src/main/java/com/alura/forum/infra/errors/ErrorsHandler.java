package com.alura.forum.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
public class ErrorsHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException e){
        List errors = e.getFieldErrors().stream().map(errorDataValidation::new).toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IntegrityValidations.class)
    public ResponseEntity errorHandlerIntegrityValidations(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerBusinessValidations(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record errorDataValidation(String field, String error){
        public errorDataValidation(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
