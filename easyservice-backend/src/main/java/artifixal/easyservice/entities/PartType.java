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
    
    /**
     * Name describing the type of part.
     */
    @Column(length=40,nullable=false)
    public String name;

    public PartType(Long id,String name){
        super(id);
        this.name=name;
    }
}
