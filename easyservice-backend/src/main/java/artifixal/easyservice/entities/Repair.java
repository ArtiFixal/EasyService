package artifixal.easyservice.entities;

import jakarta.persistence.Entity;
import java.sql.Timestamp;
import lombok.Data;

/**
 *
 * @author ArtiFixal
 */
@Data
@Entity
public class Repair extends BaseEntity{
    
    private long clientID;
    
    private long deviceID;
    
    private long statusID;
    
    private Timestamp placed;
    
    private Timestamp finished;

    public Repair(long id,long clientID,long deviceID,long statusID,Timestamp placed,Timestamp finished){
        super(id);
        this.clientID=clientID;
        this.deviceID=deviceID;
        this.statusID=statusID;
        this.placed=placed;
        this.finished=finished;
    }
}
