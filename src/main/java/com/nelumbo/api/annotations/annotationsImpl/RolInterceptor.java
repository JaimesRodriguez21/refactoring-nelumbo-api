package com.nelumbo.api.annotations.annotationsImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nelumbo.api.annotations.IngressAllowed;
import com.nelumbo.api.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class RolInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String requestUri = request.getRequestURI();
            System.out.println("Interceptando la ruta: " + requestUri);
            if (handlerMethod.getMethod().isAnnotationPresent(IngressAllowed.class)) {
                IngressAllowed annotation = handlerMethod.getMethod().getAnnotation(IngressAllowed.class);
                String[] roles = annotation.value();
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication.isAuthenticated() && !authentication.getAuthorities().isEmpty()) {
                    GrantedAuthority authority = authentication.getAuthorities().iterator().next();
                    System.out.println("Rol user login ---> "+ authority.getAuthority());
                    System.out.println("------------------------------------");
                    for (int i = 0; i < roles.length; i++) {
                        System.out.println("Rol posicion ---> "+ roles[i]);

                        if (roles[i].equals(authority.getAuthority())) {
                            return true;
                        }
                    }
                }
            }
        }
        errorException(response, HttpStatus.UNAUTHORIZED.value(), "Acceso denegado");
        return false;
    }

    protected void errorException(HttpServletResponse response, Integer httpStatus, String errorMessage) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        response.getWriter().flush();
    }

}
