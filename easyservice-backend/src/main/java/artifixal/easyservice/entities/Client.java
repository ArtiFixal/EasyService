package artifixal.easyservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * Entity class representing client.
 * 
 * @author ArtiFixal
 */
@Entity
@Table(name="clients")
@NoArgsConstructor
public class Client extends Person{

    public Client(Long id,String firstName,String lastName,String phoneNumber){
        super(id,firstName,lastName,phoneNumber);
    }
}
