package artifixal.easyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity class representing employee.
 * 
 * @author ArtiFixal
 */
@Entity
@Table(name="employees")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Employee extends Person{
    
    @Column(nullable=false)
    private String login;
    
    @Column(nullable=false)
    private String hash;

    public Employee(Long id,String firstName,String lastName,String phoneNumber,String login,String hash){
        super(id,firstName,lastName,phoneNumber);
        this.login=login;
        this.hash=hash;
    }
}
