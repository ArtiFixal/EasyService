package artifixal.easyservice.dtos;

import artifixal.easyservice.entities.Status;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author ArtiFixal
 */
@Getter
public class StatusDTO extends BaseDTO<Long>{

    @NotBlank(message="Status name can't be blank")
    @Length(max=Status.MAX_NAME_LENGTH,
            message="Status name can't exceed {max} characters")
    public String name;

    public StatusDTO(Optional<Long> id,String name){
        super(id);
        this.name=name;
    }
}
