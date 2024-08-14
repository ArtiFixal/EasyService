package artifixal.easyservice.services;

import artifixal.easyservice.dtos.ManufacturerDTO;
import artifixal.easyservice.entities.Manufacturer;
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
public class ManufacturerServiceUnitTest implements ServiceUnitTest{

    @Autowired
    @InjectMocks
    private ManufacturerService service;
    
    @Mock
    private ManufacturerRepository repo;
    
    @BeforeEach
    public void setup(){
        // this.repo is null fix
        ReflectionTestUtils.setField(service,"repo",repo);
    }

    @Test
    @Override
    public void canEditEntity(){
        Manufacturer toEdit=new Manufacturer(1l,"Man1");
        ManufacturerDTO newManufacturerData=new ManufacturerDTO(Optional.of(1l),
                "ManEdited");
        // Stubs
        when(repo.findById(any())).thenReturn(Optional.of(toEdit));
        when(repo.save(any())).thenReturn(toEdit);
        
        service.editEntity(newManufacturerData);
        
        verify(repo,times(1)).save(any());
        verify(repo,times(1)).findById(any());
        
        assertEquals(newManufacturerData.getName(),toEdit.getName());
    }

    @Test
    @Override
    public void canConvertEntityToDto(){
        Manufacturer man=new Manufacturer(1l,"Man1");
        
        ManufacturerDTO converted=service.convertEntityToDto(man);
        
        assertEquals(man.getId(),converted.getId().get());
        assertEquals(man.getName(),converted.getName());
    }

    @Test
    @Override
    public void canConvertDtoToEntity(){
        ManufacturerDTO manDto=new ManufacturerDTO(Optional.empty(),"Man1");
        
        Manufacturer converted=service.convertDtoToEntity(manDto);
        
        assertEquals(manDto.getName(),converted.getName());
    }
}
