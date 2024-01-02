package com.alura.forum.infra.errors;

public class IntegrityValidations extends RuntimeException {
    public IntegrityValidations(String s) {
        super(s);
    }
}
