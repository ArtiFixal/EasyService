package artifixal.easyservice.entities;

import lombok.Getter;

/**
 *
 * @author ArtiFixal
 */
@Getter
public class PartType extends BaseEntity{
    /**
     * Name describing the part.
     */
    public String name;

    public PartType(long id,String name){
        super(id);
        this.name=name;
    }
}
