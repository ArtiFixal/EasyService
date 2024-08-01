package artifixal.easyservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when received <b> DTO </b> is invalid.
 * 
 * @author ArtiFixal
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDTOException extends RuntimeException{

    /**
     * @param msg What is wrong.
     */
    public InvalidDTOException(String msg){
        super(msg);
    }
}
