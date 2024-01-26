package artifixal.easyservice.entities;

import lombok.Getter;

/**
 *
 * @author ArtiFixal
 */
@Getter
public abstract class Person extends BaseEntity{

    public String firstName;
    
    public String lastName;
    
    public String phoneNumber;

    public Person( long id,String firstName, String lastName, String phoneNumber) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
