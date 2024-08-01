package artifixal.easyservice.dtos.errors;

import jakarta.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error DTO containing info about multiple validation violations.
 * 
 * @author ArtiFixal
 */
@Getter
public class ValidationErrorDTO extends ErrorDTO{
    
    private ArrayList<Violation> errors;
    
    public ValidationErrorDTO(){
        super("Request parameters didn't validate");
        this.errors=new ArrayList<>();
    }
    
    public ValidationErrorDTO(Collection<ConstraintViolation<?>> errors){
        this();
        for(ConstraintViolation v:errors)
        {
            String fieldName=v.getPropertyPath().toString().split("\\.")[2];
            this.errors.add(new Violation(fieldName,v.getMessage()));
        }
    }
    
    public ValidationErrorDTO addViolation(String field,String cause){
        errors.add(new Violation(field,cause));
        return this;
    }
    
    @Getter
    @AllArgsConstructor
    protected static class Violation {
        private String field;
        private String cause;
    }
}
