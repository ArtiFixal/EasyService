package artifixal.easyservice.dtos;

import artifixal.easyservice.entities.PartType;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author ArtiFixal
 */
@Getter
public class PartTypeDTO extends BaseDTO<Long>{
    
    @NotBlank(message="Type name can't be blank")
    @Length(max=PartType.MAX_NAME_LENGTH,
            message="Type name can't exceed {max} characters")
    public String name;

    public PartTypeDTO(Optional<Long> id,String name){
        super(id);
        this.name=name;
    }
}
