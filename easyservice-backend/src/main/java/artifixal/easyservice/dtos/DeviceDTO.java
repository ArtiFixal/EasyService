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
public class DeviceDTO extends BaseDTO<Long>{
    
    public long manufacturerID;
    
    @NotBlank(message="Device name can't be blank")
    @Length(max=60,message="Device name can't exceed 40 characters")
    public String name;
    
    @NotBlank(message="Device serial number can't be blank")
    @Length(max=60,message="Device serial number can't exceed 40 characters")
    public String serialNumber;

    public DeviceDTO(Optional<Long> id,long manufacturerID,String name,String serialNumber){
        super(id);
        this.manufacturerID=manufacturerID;
        this.name=name;
        this.serialNumber=serialNumber;
    }
}
