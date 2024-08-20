package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity class representing manufacturer of part or device.
 * 
 * @author ArtiFixal
 */
@Entity
@Table(name="manufacturers")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Manufacturer extends BaseEntity{
    
    public final static int MAX_NAME_LENGTH=40;
    
    /**
     * Name of the manufacturer.
     */
    @Column(length=MAX_NAME_LENGTH,nullable=false)
    public String name;

    public Manufacturer(Long id,String name){
        super(id);
        this.name=name;
    }
}
