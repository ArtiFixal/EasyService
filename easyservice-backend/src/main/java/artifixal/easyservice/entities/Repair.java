package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity class representing client device repair.
 * 
 * @author ArtiFixal
 */
@Entity
@Table(name="repairs")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Repair extends BaseEntity{
    
    /**
     * Device owner.
     */
    @ManyToOne
    @JoinColumn(name="clientID",nullable=false)
    private Client client;
    
    /**
     * What is being repaired.
     */
    @ManyToOne
    @JoinColumn(name="deviceID",nullable=false)
    private Device device;
    
    /**
     * Current repair status.
     */
    @ManyToOne
    @JoinColumn(name="statusID",nullable=false)
    private Status repairStatus;
    
    @Column(nullable=false)
    private Timestamp placed;
    
    /**
     * Date when repair was finished. <br>
     * NULL - means that repair isn't finished yet.
     */
    private Timestamp finished;

    public Repair(Long id,Client client,Device device,Status repairStatus,Timestamp placed,Timestamp finished){
        super(id);
        this.client=client;
        this.device=device;
        this.repairStatus=repairStatus;
        this.placed=placed;
        this.finished=finished;
    }
}