package artifixal.easyservice.exceptions.handlers;

import artifixal.easyservice.dtos.errors.ErrorMessageDTO;
import artifixal.easyservice.exceptions.InvalidDTOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles {@link InvalidDTOException}.
 * 
 * @author ArtiFixal
 */
@RestControllerAdvice
public class InvalidDTOExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(value={InvalidDTOException.class})
    protected ResponseEntity handleException(InvalidDTOException ex,WebRequest request){
        return handleExceptionInternal(ex,new ErrorMessageDTO(
                ex.getLocalizedMessage()),new HttpHeaders(),
                HttpStatus.BAD_REQUEST,request);
    }
}
