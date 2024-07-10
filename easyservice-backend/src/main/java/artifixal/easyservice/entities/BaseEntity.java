package artifixal.easyservice.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.Data;

/**
 * Base class for Entities.
 * 
 * @author ArtiFixal
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private final Long id;

    public BaseEntity(Long id){
        this.id=id;
    }
}
