package artifixal.easyservice.dtos;

import artifixal.easyservice.entities.Service;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 
 * @author ArtiFixal
 */
@Getter
public class ServiceDTO extends BaseDTO<Long>{
    
    @NotBlank(message="Service name can't be blank")
    @Length(max=Service.MAX_NAME_LENGTH,
            message="Service name can't exceed {max} characters")
    public String name;
    
    @PositiveOrZero(message="Service price can't be negative")
    public BigDecimal price;

    public ServiceDTO(Optional<Long> id,String name,BigDecimal price){
        super(id);
        this.name=name;
        this.price=price;
    }
}
