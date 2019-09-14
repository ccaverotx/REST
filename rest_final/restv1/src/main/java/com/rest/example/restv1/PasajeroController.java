package com.rest.example.restv1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
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

    // tag::get-aggregate-root[]
    @GetMapping(path = "/pasajeros", produces = MediaType.APPLICATION_JSON_VALUE)
    Resources<Resource<Pasajero>> all() {

        List<Resource<Pasajero>> pasajeros = repository.findAll().stream()
                .map(pasajero -> new Resource<>(pasajero,
                        linkTo(methodOn(PasajeroController.class).one(pasajero.getId())).withSelfRel(),
                        linkTo(methodOn(PasajeroController.class).all()).withRel("pasajeros")))
                .collect(Collectors.toList());

        return new Resources<>(pasajeros,
                linkTo(methodOn(PasajeroController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/pasajeros")
    Pasajero newPasajero(@RequestBody Pasajero newPasajero) {
        return repository.save(newPasajero);
    }

    // Single item

    // tag::get-single-item[]
    @GetMapping("/pasajeros/{id}")
    Resource<Pasajero> one(@PathVariable Long id) {

        Pasajero pasajero = repository.findById(id)
                .orElseThrow(() -> new PasajeroNotFoundException(id));

        return new Resource<>(pasajero,
                linkTo(methodOn(PasajeroController.class).one(id)).withSelfRel(),
                linkTo(methodOn(PasajeroController.class).all()).withRel("pasajeros"));
    }
    // end::get-single-item[]

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
