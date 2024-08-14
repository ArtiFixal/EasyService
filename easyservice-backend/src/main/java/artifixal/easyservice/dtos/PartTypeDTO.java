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
public class PartTypeDTO extends BaseDTO<Long>{

    @NotBlank(message="Type name can't be blank")
    @Length(max=40,message="Type name can't exceed 40 characters")
    public String name;

    public PartTypeDTO(Optional<Long> id,String name){
        super(id);
        this.name=name;
    }
}
