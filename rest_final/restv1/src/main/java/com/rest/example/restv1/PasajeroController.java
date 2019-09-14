package com.rest.example.restv1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::constructor[]
@RestController
class PasajeroController {

    private final PasajeroRepo repository;

    private final PasajeroResourceAssembler assembler;

    PasajeroController(PasajeroRepo repository,
                       PasajeroResourceAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }
    // end::constructor[]

    // Aggregate root

    // tag::get-aggregate-root[]
    @GetMapping(path = "/pasajeros", produces = MediaType.APPLICATION_JSON_VALUE)
    Resources<Resource<Pasajero>> all() {

        List<Resource<Pasajero>> pasajeros = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(pasajeros,
                linkTo(methodOn(PasajeroController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // tag::post[]
    @PostMapping("/pasajeros")
    ResponseEntity<?> newPasajero(@RequestBody Pasajero newPasajero) throws URISyntaxException {

        Resource<Pasajero> resource = assembler.toResource(repository.save(newPasajero));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }
    // end::post[]

    // Single item

    // tag::get-single-item[]
    @GetMapping(path = "/pasajeros/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Resource<Pasajero> one(@PathVariable Long id) {

        Pasajero pasajero = repository.findById(id)
                .orElseThrow(() -> new PasajeroNotFoundException(id));

        return assembler.toResource(pasajero);
    }
    // end::get-single-item[]

    // tag::put[]
    @PutMapping("/pasajeros/{id}")
    ResponseEntity<?> replacePasajero(@RequestBody Pasajero newPasajero, @PathVariable Long id) throws URISyntaxException {

        Pasajero updatedPasajero = repository.findById(id)
                .map(pasajero -> {
                    pasajero.setName(newPasajero.getName());
                    pasajero.setRole(newPasajero.getRole());
                    return repository.save(pasajero);
                })
                .orElseGet(() -> {
                    newPasajero.setId(id);
                    return repository.save(newPasajero);
                });

        Resource<Pasajero> resource = assembler.toResource(updatedPasajero);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }
    // end::put[]

    // tag::delete[]
    @DeleteMapping("/pasajeros/{id}")
    ResponseEntity<?> deletePasajeros(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    // end::delete[]
}
