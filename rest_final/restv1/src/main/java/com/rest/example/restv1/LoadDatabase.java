package com.rest.example.restv1;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(PasajeroRepo repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Pasajero("Juan", "Perez", "Estudiante")));
            log.info("Preloading " + repository.save(new Pasajero("Lucas", "Castro", "Ingeniero")));
        };
    }
}
