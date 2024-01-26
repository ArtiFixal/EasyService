package artifixal.easyservice.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NonNull;

/**
 *
 * @author ArtiFixal
 */
@Entity
@Getter
public class Status extends BaseEntity{
    /**
     * Name describing the status.
     */
    @NonNull
    public String name;

    public Status(long id,String name){
        super(id);
        this.name=name;
    }
}
