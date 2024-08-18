package artifixal.easyservice.services;

import artifixal.easyservice.dtos.ServiceDTO;
import artifixal.easyservice.entities.Service;
import artifixal.easyservice.repositories.ServiceRepository;
import java.util.Optional;

/**
 *
 * @author ArtiFixal
 */
@org.springframework.stereotype.Service
public class ServiceService extends BaseService<ServiceRepository,Service,Long,ServiceDTO>{

    @Override
    protected void updateEntityValues(ServiceDTO entityNewData,Service entity){
        entity.setName(entityNewData.getName());
        entity.setPrice(entityNewData.getPrice());
    }

    @Override
    protected ServiceDTO convertEntityToDto(Service entity){
        return new ServiceDTO(Optional.of(entity.getId()),entity.getName(),
                entity.getPrice());
    }

    @Override
    protected Service convertDtoToEntity(ServiceDTO service){
        return new Service(0l,service.getName(),service.getPrice());
    }
}
