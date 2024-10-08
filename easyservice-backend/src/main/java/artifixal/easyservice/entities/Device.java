package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity class representing device.
 * 
 * @author ArtiFixal
 */
@Entity
@Table(name="devices")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Device extends BaseEntity{
    
    public final static int MAX_NAME_LENGTH=60;
    public final static int MAX_SERIAL_NUMBER_LENGTH=40;
    
    @ManyToOne
    @JoinColumn(name="manufacturerID",nullable=false)
    private Manufacturer manufacturer;
    
    @Column(length=MAX_NAME_LENGTH,nullable=false)
    public String name;
    
    @Column(length=MAX_SERIAL_NUMBER_LENGTH,nullable=false)
    public String serialNumber;
    
    @OneToMany(mappedBy="compatibleWith")
    public List<Compatibility> compatibleParts;

    public Device(Long id,Manufacturer manufacturer,String name,String serialNumber){
        super(id);
        this.manufacturer=manufacturer;
        this.name=name;
        this.serialNumber=serialNumber;
    } 
}
