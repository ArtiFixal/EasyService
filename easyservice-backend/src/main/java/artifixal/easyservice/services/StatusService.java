package artifixal.easyservice.services;

import artifixal.easyservice.dtos.StatusDTO;
import artifixal.easyservice.entities.Status;
import artifixal.easyservice.repositories.StatusRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author ArtiFixal
 */
@Service
public class StatusService extends BaseService<StatusRepository,Status,Long,StatusDTO>{

    @Override
    protected void updateEntityValues(StatusDTO entityNewData,Status entity){
        entity.setName(entityNewData.getName());
    }

    @Override
    protected StatusDTO convertEntityToDto(Status entity){
        return new StatusDTO(Optional.of(entity.getId()),entity.getName());
    }

    @Override
    protected Status convertDtoToEntity(StatusDTO entityData){
        return new Status(0l,entityData.getName());
    }
}
