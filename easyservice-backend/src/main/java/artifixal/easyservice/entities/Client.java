package artifixal.easyservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity class representing client.
 * 
 * @author ArtiFixal
 */
@Entity
@Table(name="clients")
public class Client extends Person{

    public Client(Long id,String firstName,String lastName,String phoneNumber){
        super(id,firstName,lastName,phoneNumber);
    }
}
