package artifixal.easyservice.entities;

import lombok.Getter;

/**
 *
 * @author ArtiFixal
 */
@Getter
public class Manufacturer extends BaseEntity{
    
    public String name;

    public Manufacturer(long id,String name){
        super(id);
        this.name=name;
    }
}
