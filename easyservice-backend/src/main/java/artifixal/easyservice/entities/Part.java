package artifixal.easyservice.entities;

import artifixal.easyservice.entities.parameter.Parameter;
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
 * Entity class representing part.
 * 
 * @author Acer
 */
@Entity
@Table(name="parts")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Part extends BaseEntity{
    
    public final static int MAX_NAME_LENGTH=40;
    
    @ManyToOne
    @JoinColumn(name="typeID",nullable=false)
    private PartType type;
    
    /**
     * Part manufacturer.
     */
    @ManyToOne
    @JoinColumn(name="manufacturerID",nullable=false)
    private Manufacturer manufacturer;
    
    /**
     * Name describing the part.
     */
    @Column(length=MAX_NAME_LENGTH,nullable=false)
    public String name;
    
    /**
     * Available quantity of this part.
     */
    @Column(nullable=false)
    public long quantity;
    
    @Column(name="params",columnDefinition="json",nullable=false)
    protected List<Parameter> parameters;
    
    @OneToMany(mappedBy="part")
    protected List<Compatibility> compatibleDevices;

    public Part(Long id,PartType type,Manufacturer manufacturer,String name,long quantity,List<Parameter> params) {
        super(id);
        this.type=type;
        this.manufacturer=manufacturer;
        this.name=name;
        this.quantity=quantity;
        parameters=params;
    }
}
