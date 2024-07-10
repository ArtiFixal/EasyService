package artifixal.easyservice.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Entity class representing part compatibility with devices.
 * 
 * @author ArtiFixal
 */
@Entity
@Table(name="compatibilities")
@Data
@AllArgsConstructor
public class Compatibility {
    
    @EmbeddedId
    private CompatibilityKey key;
    
    @ManyToOne
    @MapsId("partID")
    private Part part;
    
    @ManyToOne
    @MapsId("deviceID")
    private Device compatibleWith;
    
    /**
     * Notes to the compatibility.
     */
    public String note;
}
