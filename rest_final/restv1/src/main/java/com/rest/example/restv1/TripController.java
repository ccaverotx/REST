package com.rest.example.restv1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::main[]
@RestController
class TripController {

    private final TripRepo tripRepo;
    private final TripResourceAssembler assembler;

    TripController(TripRepo tripRepo,
                    TripResourceAssembler assembler) {

        this.tripRepo = tripRepo;
        this.assembler = assembler;
    }

    @GetMapping("/trips")
    Resources<Resource<Trip>> all() {

        List<Resource<Trip>> trips = tripRepo.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(trips,
                linkTo(methodOn(TripController.class).all()).withSelfRel());
    }

    @GetMapping("/trips/{id}")
    Resource<Trip> one(@PathVariable Long id) {
        return assembler.toResource(
                tripRepo.findById(id)
                        .orElseThrow(() -> new TripNotFoundException(id)));
    }

    @PostMapping("/trips")
    ResponseEntity<Resource<Trip>> newTrip(@RequestBody Trip trip) {

        trip.setStatus(Status.IN_PROGRESS);
        Trip newTrip = tripRepo.save(trip);

        return ResponseEntity
                .created(linkTo(methodOn(TripController.class).one(newTrip.getId())).toUri())
                .body(assembler.toResource(newTrip));
    }
    // end::main[]

    // tag::delete[]
    @DeleteMapping("/trips/{id}/cancel")
    ResponseEntity<ResourceSupport> cancel(@PathVariable Long id) {

        Trip trip = tripRepo.findById(id).orElseThrow(() -> new TripNotFoundException(id));

        if (trip.getStatus() == Status.IN_PROGRESS) {
            trip.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toResource(tripRepo.save(trip)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't cancel a trip that is in the " + trip.getStatus() + " status"));
    }
    // end::delete[]

    // tag::complete[]
    @PutMapping("/trips/{id}/complete")
    ResponseEntity<ResourceSupport> complete(@PathVariable Long id) {

        Trip trip = tripRepo.findById(id).orElseThrow(() -> new TripNotFoundException(id));

        if (trip.getStatus() == Status.IN_PROGRESS) {
            trip.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toResource(tripRepo.save(trip)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed", "You can't complete trip that is in the " + trip.getStatus() + " status"));
    }
    // end::complete[]
}
