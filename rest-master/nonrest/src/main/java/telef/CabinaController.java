package telef;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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
class CabinaController {

	private final CabinaRepo repository;

	private final CabinaResourceAssembler assembler;

	CabinaController(CabinaRepo repository,
					   CabinaResourceAssembler assembler) {
		
		this.repository = repository;
		this.assembler = assembler;
	}
	// end::constructor[]

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/cabinas")
	Resources<Resource<Cabina>> all() {

		List<Resource<Cabina>> cabinas = repository.findAll().stream()
			.map(assembler::toResource)
			.collect(Collectors.toList());
		
		return new Resources<>(cabinas,
			linkTo(methodOn(CabinaController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	// tag::post[]
	@PostMapping("/cabinas")
	ResponseEntity<?> newCabina(@RequestBody Cabina newCabina) throws URISyntaxException {

		Resource<Cabina> resource = assembler.toResource(repository.save(newCabina));

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}
	// end::post[]

	// Single item

	// tag::get-single-item[]
	@GetMapping("/cabinas/{id}")
	Resource<Cabina> one(@PathVariable Long id) {

		Cabina cabina = repository.findById(id)
			.orElseThrow(() -> new CabinaNotFoundException(id));
		
		return assembler.toResource(cabina);
	}
	// end::get-single-item[]

	// tag::put[]
	@PutMapping("/cabinas/{id}")
	ResponseEntity<?> replaceCabina(@RequestBody Cabina newCabina, @PathVariable Long id) throws URISyntaxException {

		Cabina updatedCabina = repository.findById(id)
			.map(cabina -> {
				cabina.setName(newCabina.getName());
				cabina.setColor(newCabina.getColor());
				return repository.save(cabina);
			})
			.orElseGet(() -> {
				newCabina.setId(id);
				return repository.save(newCabina);
			});

		Resource<Cabina> resource = assembler.toResource(updatedCabina);

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}
	// end::put[]

	// tag::delete[]
	@DeleteMapping("/cabinas/{id}")
	ResponseEntity<?> deleteCabina(@PathVariable Long id) {

		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	// end::delete[]
}
