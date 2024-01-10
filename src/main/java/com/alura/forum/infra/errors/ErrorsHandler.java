package com.alura.forum.infra.errors;

import static com.alura.forum.constants.Constants.MALFORMED_JSON_BODY;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

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
    public ResponseEntity handleSqlQueryException(SQLIntegrityConstraintViolationException e){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "An error occurred in the DataBase", e);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleErrorMethodNotSupported(HttpRequestMethodNotSupportedException ex){
         ErrorResponse errorResponse = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, ex);
         return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity handleJWTExpiredException(ExpiredJwtException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "JWT Expired", ex);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity handleSignatureException(SignatureException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "JWT Signature not valid", ex);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity handleMalformedJWTException(MalformedJwtException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "Malformed JWT", ex);
        return buildResponseEntity(errorResponse);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity handleJWTExceptions(Exception ex){
//        System.out.println("ESTE ES EL PRINTSTACK TRACE");
//        ex.printStackTrace();
//        if(ex instanceof SignatureException){
//            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "JWT Signature not valid", ex);
//            return buildResponseEntity(errorResponse);
//        }
//        else if (ex instanceof ExpiredJwtException) {
//            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "JWT Expired", ex);
//            return buildResponseEntity(errorResponse);
//        }
//        else if (ex instanceof MalformedJwtException) {
//            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "Malformed JWT", ex);
//            return buildResponseEntity(errorResponse);
//
//        } else{
//            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
//            return buildResponseEntity(errorResponse);
//        }
//    }

    private record errorDataValidation(Object object, String field, String error){
        public errorDataValidation(FieldError error){
            this(error.getObjectName(), error.getField(), error.getDefaultMessage());
        }
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
