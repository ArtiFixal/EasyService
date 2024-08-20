package artifixal.easyservice.dtos;

import artifixal.easyservice.entities.Device;
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
    @Length(max=Device.MAX_NAME_LENGTH,
            message="Device name can't exceed {max} characters")
    public String name;
    
    @NotBlank(message="Device serial number can't be blank")
    @Length(max=Device.MAX_SERIAL_NUMBER_LENGTH,
            message="Device serial number can't exceed {max} characters")
    public String serialNumber;

    public DeviceDTO(Optional<Long> id,long manufacturerID,String name,String serialNumber){
        super(id);
        this.manufacturerID=manufacturerID;
        this.name=name;
        this.serialNumber=serialNumber;
    }
}
