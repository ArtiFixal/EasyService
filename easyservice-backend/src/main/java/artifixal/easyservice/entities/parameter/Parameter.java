package artifixal.easyservice.entities.parameter;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Acer
 */
@Data
public class Parameter implements Serializable{
    private String name;
    public Object value;

    public Parameter(String name,Object value){
        this.name=name;
        this.value=value;
    }
}
