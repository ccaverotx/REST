package telef;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
class CabinaResourceAssembler implements ResourceAssembler<Cabina, Resource<Cabina>> {

	@Override
	public Resource<Cabina> toResource(Cabina cabina) {

		return new Resource<>(cabina,
			linkTo(methodOn(CabinaController.java).one(cabina.getId())).withSelfRel(),
			linkTo(methodOn(CabinaController.java).all()).withRel("cabinas"));
	}
}
