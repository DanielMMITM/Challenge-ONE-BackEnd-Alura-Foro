package com.alura.forum.infra.errors;

import static com.alura.forum.constants.Constants.MALFORMED_JSON_BODY;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleError404(EntityNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex);
        errorResponse.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException e){
        List subErrors = e.getFieldErrors().stream().map(errorDataValidation::new).toList();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, MALFORMED_JSON_BODY, e, subErrors);
        errorResponse.setMessage(e.getMessage());
        errorResponse.setDebugMessage(e.getLocalizedMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity handleError400(SQLIntegrityConstraintViolationException e){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "An error occurred in the DataBase", e);
        return buildResponseEntity(errorResponse);
    }



    private record errorDataValidation(Object object, String field, String error){
        public errorDataValidation(FieldError error){
            this(error.getObjectName(), error.getField(), error.getDefaultMessage());
        }
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
