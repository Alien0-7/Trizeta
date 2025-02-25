package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.SQLException;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // Aggiungi configurazione CORS globale per tutti gli endpoint
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")  // Permetti richieste solo da questo dominio
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Permetti questi metodi
                .allowedHeaders("*")  // Permetti tutte le intestazioni
                .allowCredentials(true);  // Consenti l'invio di credenziali (cookie, header di autorizzazione)
    }
}
