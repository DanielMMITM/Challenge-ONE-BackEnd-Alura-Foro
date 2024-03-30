package com.alura.forum.infra.errors;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Integer code;
    private HttpStatus status;
    private String message;
    private String debugMessage;
    private List<SubError> subErrors;

    public ErrorResponse(HttpStatus status, Integer code) {
        this.status = status;
        this.code = code;
    }

    public ErrorResponse(Integer code, HttpStatus status, Throwable ex) {
        this.code = code;
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ErrorResponse(Integer code, HttpStatus status, String message, Throwable ex) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    ErrorResponse(Integer code, HttpStatus status, String message, Throwable ex, List subErrors) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.subErrors = subErrors;
    }
}
