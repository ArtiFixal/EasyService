package artifixal.easyservice.dtos.errors;

import lombok.Getter;

/**
 * Error DTO with single error message.
 * 
 * @author ArtiFixal
 */
@Getter
public class ErrorMessageDTO extends ErrorDTO{

    public ErrorMessageDTO(String message){
        super(message);
    }
}
