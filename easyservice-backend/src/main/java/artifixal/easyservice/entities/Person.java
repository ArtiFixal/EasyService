package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base class for entities containing personal data.
 * 
 * @author ArtiFixal
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=true)
public abstract class Person extends BaseEntity{
    
    @Column(length=30,nullable=false)
    public String firstName;
    
    @Column(length=30,nullable=false)
    public String lastName;
    
    /**
     * Phone number with country code.
     */
    @Column(length=15,nullable=false)
    public String phoneNumber;

    public Person(Long id,String firstName, String lastName, String phoneNumber) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
