package com.nelumbo.api.annotations;

import com.nelumbo.api.annotations.annotationsImpl.RolInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final RolInterceptor rolInterceptor;

    public WebMvcConfig(RolInterceptor rolInterceptor) {
        this.rolInterceptor = rolInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rolInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/**", "/comentarios/**" );
    }
}