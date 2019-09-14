package com.rest.example.restv1;
<<<<<<< HEAD

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
=======
>>>>>>> parent of 10cd687... Controller is now RESTful :b

import java.util.List;

<<<<<<< HEAD
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
=======
>>>>>>> parent of 10cd687... Controller is now RESTful :b
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
<<<<<<< HEAD
    Resources<Resource<Pasajero>> all() {

        List<Resource<Pasajero>> pasajeros = repository.findAll().stream()
                .map(pasajero -> new Resource<>(pasajero,
                        linkTo(methodOn(PasajeroController.class).one(pasajero.getId())).withSelfRel(),
                        linkTo(methodOn(PasajeroController.class).all()).withRel("pasajeros")))
                .collect(Collectors.toList());

        return new Resources<>(pasajeros,
                linkTo(methodOn(PasajeroController.class).all()).withSelfRel());
=======
    List<Pasajero> all() {
        return repository.findAll();
>>>>>>> parent of 10cd687... Controller is now RESTful :b
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
<<<<<<< HEAD

        return new Resource<>(pasajero,
                linkTo(methodOn(PasajeroController.class).one(id)).withSelfRel(),
                linkTo(methodOn(PasajeroController.class).all()).withRel("pasajeros"));
=======
>>>>>>> parent of 10cd687... Controller is now RESTful :b
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
<<<<<<< HEAD
}
=======
}
>>>>>>> parent of 10cd687... Controller is now RESTful :b
