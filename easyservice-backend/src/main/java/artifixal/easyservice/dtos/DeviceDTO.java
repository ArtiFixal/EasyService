package artifixal.easyservice.dtos;

import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import lombok.Getter;

/**
 *
 * @author ArtiFixal
 */
@Getter
public class DeviceDTO extends BaseDTO<Long>{
    
    public long manufacturerID;
    
    @NotBlank(message="Device name can't be blank")
    public String name;
    
    @NotBlank(message="Device serial number can't be blank")
    public String serialNumber;

    public DeviceDTO(Optional<Long> id,long manufacturerID,String name,String serialNumber){
        super(id);
        this.manufacturerID=manufacturerID;
        this.name=name;
        this.serialNumber=serialNumber;
    }
}
