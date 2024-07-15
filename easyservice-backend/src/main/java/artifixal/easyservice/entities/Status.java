package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    
    /**
     * Name describing the status.
     */
    @NonNull
    @Column(length=30,nullable=false)
    public String name;

    public Status(Long id,String name){
        super(id);
        this.name=name;
    }
}
