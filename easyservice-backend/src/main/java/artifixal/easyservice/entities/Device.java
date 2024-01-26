package artifixal.easyservice.entities;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ArtiFixal
 */
@Getter
@Setter
public class Device extends BaseEntity{
    
    private long manufacturerID;
    public String name;

    public Device(long id,long manufacturerID,String name){
        super(id);
        this.manufacturerID=manufacturerID;
        this.name=name;
    } 
}
