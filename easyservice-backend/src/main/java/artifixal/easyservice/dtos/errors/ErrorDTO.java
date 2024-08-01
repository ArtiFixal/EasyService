package artifixal.easyservice.dtos.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Base class for all DTO's related to errors.
 * 
 * @author ArtiFixal
 */
@Getter
@AllArgsConstructor
public abstract class ErrorDTO {
    
    /**
     * Message describing error.
     */
    public String message;
}
