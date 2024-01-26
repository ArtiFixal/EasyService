package artifixal.easyservice.entities;

import lombok.Getter;

/**
 *
 * @author ArtiFixal
 */
@Getter
public class Employee extends Person{

    private String login;
    private String hash;

    public Employee(long id,String firstName,String lastName,String phoneNumber,String login,String hash){
        super(id,firstName,lastName,phoneNumber);
        this.login=login;
        this.hash=hash;
    }
}
