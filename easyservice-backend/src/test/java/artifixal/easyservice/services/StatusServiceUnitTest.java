package artifixal.easyservice.services;

import artifixal.easyservice.dtos.StatusDTO;
import artifixal.easyservice.entities.Status;
import artifixal.easyservice.repositories.StatusRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author ArtiFixal
 */
@ExtendWith(MockitoExtension.class)
public class StatusServiceUnitTest implements ServiceUnitTest{
    
    @InjectMocks
    private StatusService service;
    
    @Mock
    private StatusRepository repo;
    
    @BeforeEach
    public void setup(){
        ReflectionTestUtils.setField(service,"repo",repo);
    }

    @Override
    public void canEditEntity(){
        Status toEdit=new Status(1l,"Status1");
        StatusDTO newStatusData=new StatusDTO(Optional.of(1l),"StatusEdited");
        
        when(repo.findById(any())).thenReturn(Optional.of(toEdit));
        when(repo.save(any())).thenReturn(toEdit);
        
        service.editEntity(newStatusData);
        
        verify(repo,times(1)).save(any());
        verify(repo,times(1)).findById(any());
        
        assertEquals(newStatusData.getName(),toEdit.getName());
    }

    @Override
    public void canConvertEntityToDto(){
        Status toConvert=new Status(1l,"Service1");
        
        StatusDTO converted=service.convertEntityToDto(toConvert);
        
        assertEquals(toConvert.getId(),converted.getId().get());
        assertEquals(toConvert.getName(),converted.getName());
    }

    @Override
    public void canConvertDtoToEntity(){
        StatusDTO toConvert=new StatusDTO(Optional.empty(),"Service1");
        
        Status converted=service.convertDtoToEntity(toConvert);
        
        assertEquals(toConvert.getName(),converted.getName());
    }
}
