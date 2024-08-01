package artifixal.easyservice.services;

import artifixal.easyservice.dtos.DeviceDTO;
import artifixal.easyservice.entities.Device;
import artifixal.easyservice.entities.Manufacturer;
import artifixal.easyservice.exceptions.ChildEntityNotFoundException;
import artifixal.easyservice.repositories.DeviceRepository;
import artifixal.easyservice.repositories.ManufacturerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ArtiFixal
 */
@Service
public class DeviceService extends BaseService<DeviceRepository,Device,Long,DeviceDTO>{
    
    @Autowired
    private ManufacturerRepository manufacturerRepo;
    
    private Manufacturer getManufacturerByID(Long id) throws ChildEntityNotFoundException{
        return manufacturerRepo.findById(id).orElseThrow(()->
                new ChildEntityNotFoundException("Manufacturer",id));
    }

    @Override
    protected void updateEntityValues(DeviceDTO editedData,Device editedDevice){
        if(editedData.getManufacturerID()!=editedDevice.getManufacturer().getId())
        {
            Manufacturer newManufacturer=getManufacturerByID(editedData.getManufacturerID());
            editedDevice.setManufacturer(newManufacturer);
        }
        editedDevice.setName(editedData.getName());
        editedDevice.setSerialNumber(editedData.getSerialNumber());
    }

    @Override
    protected DeviceDTO convertEntityToDto(Device entity){
        return new DeviceDTO(Optional.of(entity.getId()),
                entity.getManufacturer().getId(),entity.getName(),
                entity.getSerialNumber());
    }

    @Override
    protected Device convertDtoToEntity(DeviceDTO device){
        final Manufacturer m=getManufacturerByID(device.getManufacturerID());
        return new Device(0l,m,device.getName(),
                device.getSerialNumber());
    }
}
