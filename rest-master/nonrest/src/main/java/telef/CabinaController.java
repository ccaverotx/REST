package telef;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CabinaController {
    private final CabinaRepo repository;

	CabinaController(CabinaRepo repository) {
		this.repository = repository;
	}

	

	@GetMapping("/cabinas")
	List<Cabina> all() {
		return repository.findAll();
	}

	@PostMapping("/cabinas")
	Cabina newCabina(@RequestBody Cabina newCabina) {
		return repository.save(newCabina);
	}

	// Single item
	
	/*@GetMapping("/cabinas/{id}")
	Cabina one(@PathVariable Long id) {
		
		return repository.findById(id)
			.orElseThrow(() -> new CabinaNotFoundException(id));
	}*/
	
	@GetMapping("/cabinas/{id}")
	Resource<Cabina> one(@PathVariable Long id) {

	  Cabina cabina = repository.findById(id)
	    .orElseThrow(() -> new CabinaNotFoundException(id));

	  return new Resource<>(cabina,
	    linkTo(methodOn(CabinaController.java).one(id)).withSelfRel(),
	    linkTo(methodOn(CabinaController.java).all()).withRel("cabinas"));
	}

	@PutMapping("/cabinas/{id}")
	Cabina replaceCabina(@RequestBody Cabina newCabina, @PathVariable Long id) {
		
		return repository.findById(id)
			.map(cabina -> {
				cabina.setName(newCabina.getName());
				cabina.setColor(newCabina.getColor());
				return repository.save(cabina);
			})
			.orElseGet(() -> {
				newCabina.setId(id);
				return repository.save(newCabina);
			});
	}

	@DeleteMapping("/cabinas/{id}")
	void deleteCabina(@PathVariable Long id) {
		repository.deleteById(id);
	}

}

