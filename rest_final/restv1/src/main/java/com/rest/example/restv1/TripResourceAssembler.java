package com.rest.example.restv1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class TripResourceAssembler implements ResourceAssembler<Trip, Resource<Trip>> {

    @Override
    public Resource<Trip> toResource(Trip trip) {

        // Unconditional links to single-item resource and aggregate root

        Resource<Trip> tripResource = new Resource<>(trip,
                linkTo(methodOn(TripController.class).one(trip.getId())).withSelfRel(),
                linkTo(methodOn(TripController.class).all()).withRel("trips")
        );

        // Conditional links based on state of the trip

        if (trip.getStatus() == Status.IN_PROGRESS) {
            tripResource.add(
                    linkTo(methodOn(TripController.class)
                            .cancel(trip.getId())).withRel("cancel"));
            tripResource.add(
                    linkTo(methodOn(TripController.class)
                            .complete(trip.getId())).withRel("complete"));
        }

        return tripResource;
    }
}
