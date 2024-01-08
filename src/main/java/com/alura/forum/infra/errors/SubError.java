package com.alura.forum.infra.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

abstract class SubError {

}

@Data
@EqualsAndHashCode
@AllArgsConstructor
class ValidationError extends SubError{
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ValidationError(String object, String message){
        this.object = object;
        this.message = message;
    }

    public ValidationError(String object, String field, String message){
        this.object = object;
        this.field = field;
        this.message = message;
    }
}
