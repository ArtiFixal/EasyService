package artifixal.easyservice.dtos;

import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author ArtiFixal
 */
@Getter
public class ManufacturerDTO extends BaseDTO<Long>{
    
    @NotBlank(message="Manufacturer name can't be blank")
    @Length(max=40,message="Manufacturer name can't exceed 40 characters")
    public String name;

    public ManufacturerDTO(Optional<Long> id,String name){
        super(id);
        this.name=name;
    }
}
