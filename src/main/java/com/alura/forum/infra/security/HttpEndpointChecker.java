package com.alura.forum.infra.security;

import static com.alura.forum.constants.Constants.EMPTY;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

@Component
public class HttpEndpointChecker {
    @Autowired
    private DispatcherServlet dispatcherServlet;

    public boolean isEndpointExist(HttpServletRequest request) {
        for (HandlerMapping handlerMapping : dispatcherServlet.getHandlerMappings()) {
            try {
                HandlerExecutionChain foundHandler = handlerMapping.getHandler(request);
                if (foundHandler != null || foundHandler.toString() != EMPTY) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
