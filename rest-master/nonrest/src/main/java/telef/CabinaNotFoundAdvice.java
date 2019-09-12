package telef;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class CabinaNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(CabinaNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String cabinaNotFoundHandler(CabinaNotFoundException ex) {
		return ex.getMessage();
	}
}
