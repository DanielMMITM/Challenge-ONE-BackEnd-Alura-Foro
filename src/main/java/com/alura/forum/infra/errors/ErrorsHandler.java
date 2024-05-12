package com.alura.forum.infra.errors;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.mapping.UniqueKey;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import static com.alura.forum.constants.Constants.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleError404(EntityNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_NOT_FOUND, HttpStatus.NOT_FOUND, ex);
        errorResponse.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleBadCredentials(BadCredentialsException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, HttpStatus.UNAUTHORIZED, INVALID_CREDENTIALS, ex);
        errorResponse.setDebugMessage(ex.getLocalizedMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException e){
        List subErrors = e.getFieldErrors().stream().map(errorDataValidation::new).toList();
        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_BAD_REQUEST, HttpStatus.BAD_REQUEST, MALFORMED_JSON_BODY, e, subErrors);
        errorResponse.setMessage(e.getMessage());
        errorResponse.setDebugMessage(e.getLocalizedMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity handleSqlQueryException(SQLIntegrityConstraintViolationException e){
        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_CONFLICT, HttpStatus.CONFLICT, DATABASE_ERROR, e);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleErrorMethodNotSupported(HttpRequestMethodNotSupportedException ex){
         ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED, ex);
         return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity handleJWTExpiredException(ExpiredJwtException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, HttpStatus.UNAUTHORIZED, EXPIRED_JWT, ex);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity handleSignatureException(SignatureException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, HttpStatus.UNAUTHORIZED, SIGNATURE_NOT_VALID, ex);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity handleMalformedJWTException(MalformedJwtException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, HttpStatus.UNAUTHORIZED, MALFORMED_JWT, ex);
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
