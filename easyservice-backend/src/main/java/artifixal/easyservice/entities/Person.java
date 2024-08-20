package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Base class for entities containing personal data.
 * 
 * @author ArtiFixal
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public abstract class Person extends BaseEntity{
    
    public final static int MAX_FIRST_NAME_LENGTH=30;
    public final static int MAX_LAST_NAME_LENGTH=30;
    public final static int MAX_PHONE_NUMBER_LENGTH=30;
    
    @Column(length=MAX_FIRST_NAME_LENGTH,nullable=false)
    public String firstName;
    
    @Column(length=MAX_LAST_NAME_LENGTH,nullable=false)
    public String lastName;
    
    /**
     * Phone number with country code.
     */
    @Column(length=MAX_PHONE_NUMBER_LENGTH,nullable=false)
    public String phoneNumber;

    public Person(Long id,String firstName, String lastName, String phoneNumber) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
