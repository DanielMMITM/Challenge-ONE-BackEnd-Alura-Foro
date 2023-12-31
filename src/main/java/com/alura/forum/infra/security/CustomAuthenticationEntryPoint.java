package com.alura.forum.infra.security;

import com.alura.forum.infra.errors.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.security.core.AuthenticationException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException{
            ErrorResponse re = new ErrorResponse(HttpStatus.UNAUTHORIZED, authException);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            OutputStream responseStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(responseStream, re);
            responseStream.flush();
    }
}
