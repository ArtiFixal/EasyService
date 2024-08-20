package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity class representing possible {@link Repair} status.
 *
 * @author ArtiFixal
 */
@Entity
@Table(name="statuses")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Status extends BaseEntity{
    
    public final static int MAX_NAME_LENGTH=30;
    
    /**
     * Name describing the status.
     */
    @Column(length=MAX_NAME_LENGTH,nullable=false)
    public String name;

    public Status(Long id,String name){
        super(id);
        this.name=name;
    }
}
