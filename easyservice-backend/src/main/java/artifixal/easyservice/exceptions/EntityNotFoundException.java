package artifixal.easyservice.exceptions;

import lombok.Getter;

/**
 * Thrown by {@code Service} when {@code Entity} wasn't foud in the DB.
 * 
 * @author ArtiFixal
 */
@Getter
public class EntityNotFoundException extends RuntimeException{

    private final String entityName;
    
    private final Object entityID;
    
    public EntityNotFoundException(String entityName,Object entityID){
        super(entityName+" with ID "+entityID+" not found");
        this.entityID=entityID;
        this.entityName=entityName;
    }
}
