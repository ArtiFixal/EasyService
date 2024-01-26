package artifixal.easyservice.entities;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Acer
 */
@Getter
@Setter
public class Parameter{
    private String name;
    public Object value;

    public Parameter(String name,Object value){
        this.name=name;
        this.value=value;
    }
}
