package artifixal.easyservice.entities.parameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

/**
 * Converts parameters JSON column of {@code Part} table to {@code String}.
 * 
 * @author ArtiFixal
 * 
 * @see artifixal.easyservice.entities.Part
 */
@Converter(autoApply=true)
public class JsonParameterConverter implements AttributeConverter<List<Parameter>,String>{

    private static final ObjectMapper mapper=new ObjectMapper();
    
    @Override
    public String convertToDatabaseColumn(List<Parameter> parameters){
        try{
            mapper.writeValueAsString(parameters);
        }catch(JsonProcessingException e){
            
        }
        return null;
    }

    @Override
    public List<Parameter> convertToEntityAttribute(String json){
        try{
            mapper.readTree(json);
        }catch(JsonProcessingException e){
            
        }
        return null;
    }

}
