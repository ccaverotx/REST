package com.rest.example.restv1;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PasajeroController {

    private final PasajeroRepo repository;

    PasajeroController(PasajeroRepo repository) {
        this.repository = repository;
    }

    // Aggregate root

    @GetMapping("/pasajeros")
    List<Pasajero> all() {
        return repository.findAll();
    }

    @PostMapping("/pasajeros")
    Pasajero newPasajero(@RequestBody Pasajero newPasajero) {
        return repository.save(newPasajero);
    }

    // Single item

    @GetMapping("/pasajeros/{id}")
    Pasajero one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new PasajeroNotFoundException(id));
    }

    @PutMapping("/pasajeros/{id}")
    Pasajero replacePasajero(@RequestBody Pasajero newPasajero, @PathVariable Long id) {

        return repository.findById(id)
                .map(pasajero -> {
                    pasajero.setName(newPasajero.getName());
                    pasajero.setRole(newPasajero.getRole());
                    return repository.save(pasajero);
                })
                .orElseGet(() -> {
                    newPasajero.setId(id);
                    return repository.save(newPasajero);
                });
    }

    @DeleteMapping("/pasajeros/{id}")
    void deletePasajero(@PathVariable Long id) {
        repository.deleteById(id);
    }
}