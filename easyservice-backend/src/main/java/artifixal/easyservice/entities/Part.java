package artifixal.easyservice.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author Acer
 */
@Getter
public class Part extends BaseEntity{
    
    private long typeID;
    
    private long manufacturerID;
    
    public String name;
    
    public long quantity;
    
    protected List<Parameter> parameters;

    public Part(long id,long typeID,long manufacturerID,String name,long quantity,InputStream jsonStream) throws IOException{
        super(id);
        this.typeID=typeID;
        this.manufacturerID=manufacturerID;
        this.name=name;
        this.quantity=quantity;
        parameters=readParameters(jsonStream);
    }
    
    private List<Parameter> readParameters(InputStream jsonStream) throws IOException{
        final ObjectMapper mapper=new JsonMapper();
        return mapper.treeToValue(mapper.readTree(jsonStream),ArrayList.class);
    }
    
}
