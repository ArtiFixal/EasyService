package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity class representing type of part.
 * 
 * @author ArtiFixal
 */
@Entity
@Table(name="types")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class PartType extends BaseEntity{
    
    public final static int MAX_NAME_LENGTH=40;
    
    /**
     * Name describing the type of part.
     */
    @Column(length=MAX_NAME_LENGTH,nullable=false)
    public String name;

    public PartType(Long id,String name){
        super(id);
        this.name=name;
    }
}
