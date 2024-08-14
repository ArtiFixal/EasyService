package artifixal.easyservice.services;

import artifixal.easyservice.dtos.ManufacturerDTO;
import artifixal.easyservice.entities.Manufacturer;
import artifixal.easyservice.repositories.ManufacturerRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author ArtiFixal
 */
@Service
public class ManufacturerService extends BaseService<ManufacturerRepository,Manufacturer,Long,ManufacturerDTO>{

    @Override
    protected void updateEntityValues(ManufacturerDTO entityNewData,Manufacturer entity){
        entity.setName(entityNewData.getName());
    }

    @Override
    protected ManufacturerDTO convertEntityToDto(Manufacturer entity){
        return new ManufacturerDTO(Optional.of(entity.getId()),entity.getName());
    }

    @Override
    protected Manufacturer convertDtoToEntity(ManufacturerDTO entityData){
        return new Manufacturer(0l,entityData.getName());
    }
}
