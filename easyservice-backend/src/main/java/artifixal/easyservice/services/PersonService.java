package artifixal.easyservice.services;

import artifixal.easyservice.dtos.PersonDTO;
import artifixal.easyservice.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author ArtiFixal
 * @param <R>
 * @param <ET>
 * @param <DTO>
 */
@Service
public abstract class PersonService<R extends JpaRepository<ET,Long>,ET extends Person,DTO extends PersonDTO> extends BaseService<R,ET,Long,DTO>{
    
    protected void updatePersonalData(DTO entityNewData,ET entity){
        entity.setFirstName(entityNewData.getFirstName());
        entity.setLastName(entityNewData.getLastName());
        entity.setPhoneNumber(entityNewData.getPhoneNumber());
    }

}
