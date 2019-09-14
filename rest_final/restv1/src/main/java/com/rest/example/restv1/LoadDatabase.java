package com.rest.example.restv1;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(PasajeroRepo pasajeroRepo,
                                   TripRepo tripRepo) {
        return args -> {
            pasajeroRepo.save(new Pasajero("Juan", "Perez", "Estudiante"));
            pasajeroRepo.save(new Pasajero("Reinaldo", "Veloz", "Ingeniero"));

            pasajeroRepo.findAll().forEach(pasajero -> {
                log.info("Preloaded " + pasajero);
            });

            // tag::trip[]
            tripRepo.save(new Trip("Tramo Largo", Status.COMPLETED));
            tripRepo.save(new Trip("Tramo intermedio", Status.IN_PROGRESS));

            tripRepo.findAll().forEach(trip-> {
                log.info("Preloaded " + trip);
            });
            // end::trip[]
        };
    }
}
