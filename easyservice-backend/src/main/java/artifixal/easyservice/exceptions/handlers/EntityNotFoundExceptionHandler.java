package artifixal.easyservice.exceptions.handlers;

import artifixal.easyservice.dtos.errors.ErrorMessageDTO;
import artifixal.easyservice.exceptions.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles {@link EntityNotFoundException} and its descendants.
 * 
 * @author ArtiFixal
 */
@RestControllerAdvice
public class EntityNotFoundExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(value={EntityNotFoundException.class})
    protected ResponseEntity handleException(EntityNotFoundException ex,WebRequest request){
        return handleExceptionInternal(ex,new ErrorMessageDTO(request.getContextPath()),new HttpHeaders(),
                HttpStatus.NOT_FOUND,request);
    }
}
