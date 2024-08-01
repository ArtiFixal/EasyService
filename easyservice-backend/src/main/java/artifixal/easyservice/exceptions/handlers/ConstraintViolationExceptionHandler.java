package artifixal.easyservice.exceptions.handlers;

import artifixal.easyservice.dtos.errors.ValidationErrorDTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles Jakarta validation exceptions {@link ConstraintViolationException}.
 * 
 * @author ArtiFixal
 * @see jakarta.validation.Valid
 */
@RestControllerAdvice
public class ConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler({ConstraintViolationException.class,ValidationException.class})
    protected ResponseEntity handleException(ConstraintViolationException ex,WebRequest request){
        ValidationErrorDTO errorDto=new ValidationErrorDTO(ex.getConstraintViolations());
        return handleExceptionInternal(ex,errorDto,new HttpHeaders(),
                HttpStatus.BAD_REQUEST,request);
    }
}
