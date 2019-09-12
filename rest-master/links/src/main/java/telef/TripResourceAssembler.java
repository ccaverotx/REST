package telef;

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
			linkTo(methodOn(TripController.java).one(trip.getId())).withSelfRel(),
			linkTo(methodOn(TripController.java).all()).withRel("trips")
		);

		// Conditional links based on state of the order
		
		if (trip.getStatus() == Status.IN_PROGRESS) {
			tripResource.add(
				linkTo(methodOn(TripController.java)
					.cancel(trip.getId())).withRel("cancel"));
			tripResource.add(
				linkTo(methodOn(TripController.java)
					.complete(trip.getId())).withRel("complete"));
		}

		return tripResource;
	}
}
