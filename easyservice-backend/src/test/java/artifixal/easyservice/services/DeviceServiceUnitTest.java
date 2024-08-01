package artifixal.easyservice.services;

import artifixal.easyservice.dtos.DeviceDTO;
import artifixal.easyservice.entities.Device;
import artifixal.easyservice.entities.Manufacturer;
import artifixal.easyservice.repositories.DeviceRepository;
import artifixal.easyservice.repositories.ManufacturerRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author ArtiFixal
 */
@ExtendWith(MockitoExtension.class)
public class DeviceServiceUnitTest implements ServiceUnitTest{
    
    @Autowired
    @InjectMocks
    private DeviceService service;
    
    @Mock
    private DeviceRepository repo;
    
    @Mock
    private ManufacturerRepository manufacturerRepo;
    
    @BeforeEach
    public void setup(){
        // this.repo is null fix
        ReflectionTestUtils.setField(service,"repo",repo);
    }
    
    @Test
    @Override
    public void canAddEntity(){
        Manufacturer manufacturer=new Manufacturer(1l,"Man1");
        DeviceDTO newDevice=new DeviceDTO(Optional.empty(),
                manufacturer.getId(),"DeviceAdd","DA111");
        // Stubs
        when(manufacturerRepo.findById(any()))
                .thenReturn(Optional.of(manufacturer));
        Device toReturn=service.convertDtoToEntity(newDevice);
        when(repo.save(any()))
                .thenReturn(toReturn);
        
        Device saved=service.addEntity(newDevice);
        
        // Verify calls
        verify(repo,times(1)).save(any());
        verify(manufacturerRepo,times(2)).findById(any());
        
        // Asertions
        assertEquals(newDevice.getName(),saved.getName());
        assertEquals(newDevice.getSerialNumber(),saved.getSerialNumber());
    }
    
    @Test
    @Override
    public void canEditEntity(){
        Manufacturer manufacturer=new Manufacturer(1l,"Man1"), 
                newManufacturerData=new Manufacturer(2l,"Man2");
        Device toEdit=new Device(1l,manufacturer,"DeviceEdit","DE111");
        DeviceDTO newDeviceData=new DeviceDTO(Optional.of(1l),
                newManufacturerData.getId(),"DeviceEdited","DE222");
        // Stubs
        when(repo.findById(any())).thenReturn(Optional.of(toEdit));
        when(repo.save(any())).thenReturn(toEdit);
        when(manufacturerRepo.findById(any()))
                .thenReturn(Optional.of(newManufacturerData));
        
        service.editEntity(newDeviceData);
        
        // Verify calls
        verify(repo,times(1)).save(any());
        verify(repo,times(1)).findById(any());
        verify(manufacturerRepo,times(1)).findById(any());
        
        // Assertions
        assertEquals(newDeviceData.getManufacturerID(),
                toEdit.getManufacturer().getId());
        assertEquals(newDeviceData.getName(),toEdit.getName());
        assertEquals(newDeviceData.getSerialNumber(),toEdit.getSerialNumber());
    }
    
    @Test
    @Override
    public void canConvertEntityToDto(){
        Manufacturer man=new Manufacturer(1l,"Man1");
        Device dev=new Device(1l,man,"Good PC 1","GPC111");
        
        DeviceDTO converted=service.convertEntityToDto(dev);
        
        assertEquals(dev.getId(),converted.getId().get());
        assertEquals(dev.getManufacturer().getId(),converted.getManufacturerID());
        assertEquals(dev.getName(),converted.getName());
        assertEquals(dev.getSerialNumber(),converted.getSerialNumber());
    }
    
    @Test
    @Override
    public void canConvertDtoToEntity(){
        Manufacturer man=new Manufacturer(1l,"Man1");
        DeviceDTO devDto=new DeviceDTO(Optional.empty(),man.getId(),"Good PC 2"
                ,"GPC222");
        // Stubs
        when(manufacturerRepo.findById(any())).thenReturn(Optional.of(man));
        
        Device converted=service.convertDtoToEntity(devDto);
        verify(manufacturerRepo,times(1)).findById(any());
        
        assertEquals(devDto.getManufacturerID(),converted.getManufacturer().getId());
        assertEquals(devDto.getName(),converted.getName());
        assertEquals(devDto.getSerialNumber(),converted.getSerialNumber());
    }
}
