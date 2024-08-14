package artifixal.easyservice.services;

import artifixal.easyservice.dtos.PartTypeDTO;
import artifixal.easyservice.entities.PartType;
import artifixal.easyservice.repositories.PartTypeRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author ArtiFixal
 */
@Service
public class PartTypeService extends BaseService<PartTypeRepository,PartType,Long,PartTypeDTO>{

    @Override
    protected void updateEntityValues(PartTypeDTO entityNewData,PartType entity){
        entity.setName(entityNewData.getName());
    }

    @Override
    protected PartTypeDTO convertEntityToDto(PartType entity){
        return new PartTypeDTO(Optional.of(entity.getId()),entity.getName());
    }

    @Override
    protected PartType convertDtoToEntity(PartTypeDTO entityData){
        return new PartType(0l,entityData.getName());
    }
}
