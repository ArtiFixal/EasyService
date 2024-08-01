package artifixal.easyservice.exceptions;

import lombok.Getter;

/**
 * Thrown by {@code Service} when {@code Entity} child wasn't found in the DB.
 * 
 * @author ArtiFixal
 */
@Getter
public class ChildEntityNotFoundException extends EntityNotFoundException{

    public ChildEntityNotFoundException(String entityName,Object entityID){
        super(entityName,entityID);
    }
}
