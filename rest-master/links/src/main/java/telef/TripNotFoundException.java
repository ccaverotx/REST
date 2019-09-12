package telef;

class TripNotFoundException extends RuntimeException {

	TripNotFoundException(Long id) {
		super("No se pudo encontrar " + id);
	}
}
