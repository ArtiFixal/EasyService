package artifixal.easyservice.entities;

import artifixal.easyservice.entities.parameter.Parameter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity class representing part.
 * 
 * @author Acer
 */
@Entity
@Table(name="parts")
@Data
@EqualsAndHashCode(callSuper=true)
public class Part extends BaseEntity{
    
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
    @Column(length=40,nullable=false)
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

    public Part(Long id,PartType type,Manufacturer manufacturer,String name,long quantity,List<Parameter> params) throws IOException, SQLException{
        super(id);
        this.type=type;
        this.manufacturer=manufacturer;
        this.name=name;
        this.quantity=quantity;
        parameters=params;
    }
}
