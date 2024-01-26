package artifixal.easyservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * @author ArtiFixal
 */
@Entity
@Getter
@EqualsAndHashCode
public abstract class BaseEntity {
    @Id
    private final long id;

    public BaseEntity(long id){
        this.id=id;
    }
}
