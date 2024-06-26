package com.br.login.infra.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	//PERMISSAO PARA TODAS REQUISICOES VENHAM DA PORTA 4200 E 8080
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // "http://localhost:8080"
                .allowedMethods("GET", "POST", "DELETE", "PUT");
    }
}
