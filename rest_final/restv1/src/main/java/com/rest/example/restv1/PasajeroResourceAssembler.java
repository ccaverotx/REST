package com.rest.example.restv1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class PasajeroResourceAssembler implements ResourceAssembler<Pasajero, Resource<Pasajero>> {

    @Override
    public Resource<Pasajero> toResource(Pasajero pasajero) {

        return new Resource<>(pasajero,
                linkTo(methodOn(PasajeroController.class).one(pasajero.getId())).withSelfRel(),
                linkTo(methodOn(PasajeroController.class).all()).withRel("pasajeros"));
    }
}