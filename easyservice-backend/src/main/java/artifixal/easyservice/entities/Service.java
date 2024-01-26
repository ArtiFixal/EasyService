package artifixal.easyservice.entities;

import jakarta.persistence.Entity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ArtiFixal
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Service extends BaseEntity{
    /**
     * Name describing the service.
     */
    private String name;
    
    /**
     * Price of the service
     */
    private BigDecimal price;

    public Service(long id,String name,BigDecimal price){
        super(id);
        this.name=name;
        this.price=price;
    }
}
