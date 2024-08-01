package artifixal.easyservice.dtos;

import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ArtiFixal
 */
@Getter
@Setter
public abstract class PersonDTO extends BaseDTO<Long>{
    public String firstName;
    public String lastName;
    public String phoneNumber;
    
    public PersonDTO(String firstName,String lastName,String phoneNumber){
        this(Optional.empty(),firstName,lastName,phoneNumber);
    }

    public PersonDTO(Optional<Long> id,String firstName,String lastName,String phoneNumber){
        super(id);
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
    }
}
