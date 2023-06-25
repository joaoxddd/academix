package com.comux.academix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UsuarioInterceptor usuarioInterceptor;

    @Autowired
    public WebMvcConfig(UsuarioInterceptor usuarioInterceptor) {
        this.usuarioInterceptor = usuarioInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(usuarioInterceptor);
    }
}
