package telef;

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

	private final TripRepository tripRepository;
	private final TripResourceAssembler assembler;

	TripController(TripRepository tripRepository,
					TripResourceAssembler assembler) {

		this.tripRepository = tripRepository;
		this.assembler = assembler;
	}

	@GetMapping("/trips")
	Resources<Resource<Trip>> all() {

		List<Resource<Trip>> trips = tripRepository.findAll().stream()
			.map(assembler::toResource)
			.collect(Collectors.toList());

		return new Resources<>(trips,
			linkTo(methodOn(TripController.java).all()).withSelfRel());
	}

	@GetMapping("/trips/{id}")
	Resource<Trip> one(@PathVariable Long id) {
		return assembler.toResource(
			tripRepository.findById(id)
				.orElseThrow(() -> new TripNotFoundException(id)));
	}

	@PostMapping("/trips")
	ResponseEntity<Resource<Trip>> newTrip(@RequestBody Trip trip) {

		trip.setStatus(Status.IN_PROGRESS);
		Trip newTrip = tripRepository.save(trip);

		return ResponseEntity
			.created(linkTo(methodOn(TripController.class).one(newTrip.getId())).toUri())
			.body(assembler.toResource(newTrip));
	}
	// end::main[]
	
	// tag::delete[]
	@DeleteMapping("/trips/{id}/cancel")
	ResponseEntity<ResourceSupport> cancel(@PathVariable Long id) {

		Trip trip = tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(id));

		if (trip.getStatus() == Status.IN_PROGRESS) {
			trip.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(assembler.toResource(tripRepository.save(trip)));
		}

		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.body(new VndErrors.VndError("Method not allowed", "No puedes eliminar el viaje " + trip.getStatus() + " status"));
	}
	// end::delete[]

	// tag::complete[]
	@PutMapping("/trips/{id}/complete")
	ResponseEntity<ResourceSupport> complete(@PathVariable Long id) {
		
			Trip trip = TripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(id));

			if (trip.getStatus() == Status.IN_PROGRESS) {
				trip.setStatus(Status.COMPLETED);
				return ResponseEntity.ok(assembler.toResource(tripRepository.save(trip)));
			}

			return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(new VndErrors.VndError("Method not allowed", "viaje " + trip.getStatus() + " status"));
	}
	// end::complete[]
}
