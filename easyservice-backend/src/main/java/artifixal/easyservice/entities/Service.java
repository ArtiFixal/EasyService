package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity class representing service performed during repair.
 *
 * @author ArtiFixal
 */
@Entity
@Table(name="services")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Service extends BaseEntity{
    
    /**
     * Name describing the service.
     */
    @Column(length=40,nullable=false)
    private String name;
    
    /**
     * Price of the service.
     */
    @Column(precision=10,scale=2,nullable=false)
    private BigDecimal price;

    public Service(Long id,String name,BigDecimal price){
        super(id);
        this.name=name;
        this.price=price;
    }
}
