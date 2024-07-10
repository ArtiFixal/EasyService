package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity class representing manufacturer of part or device.
 * 
 * @author ArtiFixal
 */
@Entity
@Table(name="manufacturers")
@Data
@EqualsAndHashCode(callSuper=true)
public class Manufacturer extends BaseEntity{
    
    /**
     * Name of the manufacturer.
     */
    @Column(length=40,nullable=false)
    public String name;

    public Manufacturer(Long id,String name){
        super(id);
        this.name=name;
    }
}
