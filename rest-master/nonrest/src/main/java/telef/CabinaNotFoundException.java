package telef;

class CabinaNotFoundException extends RuntimeException {

	CabinaNotFoundException(Long id) {
		super("No se pudo encontrar la cabina " + id);
	}
}
