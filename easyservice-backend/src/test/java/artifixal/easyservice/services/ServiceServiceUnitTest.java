package artifixal.easyservice.services;

import artifixal.easyservice.dtos.ServiceDTO;
import artifixal.easyservice.entities.Service;
import artifixal.easyservice.repositories.ServiceRepository;
import java.math.BigDecimal;
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
public class ServiceServiceUnitTest implements ServiceUnitTest{

    @Autowired
    @InjectMocks
    private ServiceService service;
    
    @Mock
    private ServiceRepository repo;
    
    @BeforeEach
    public void setup(){
        ReflectionTestUtils.setField(service,"repo",repo);
    }
    
    @Test
    @Override
    public void canEditEntity(){
        Service toEdit=new Service(1l,"Service1",BigDecimal.TEN);
        ServiceDTO newServiceData=new ServiceDTO(Optional.of(1l),"ServiceEdited",
                BigDecimal.ONE);
        
        when(repo.findById(any())).thenReturn(Optional.of(toEdit));
        when(repo.save(any())).thenReturn(toEdit);
        
        service.editEntity(newServiceData);
        
        verify(repo,times(1)).save(any());
        verify(repo,times(1)).findById(any());
        
        assertEquals(newServiceData.getId().get(),toEdit.getId());
        assertEquals(newServiceData.getName(),toEdit.getName());
        assertEquals(newServiceData.getPrice(),toEdit.getPrice());
    }

    @Test
    @Override
    public void canConvertEntityToDto(){
        Service toConvert=new Service(1l,"Service1",BigDecimal.ONE);
        
        ServiceDTO converted=service.convertEntityToDto(toConvert);
        
        assertEquals(toConvert.getId(),converted.getId().get());
        assertEquals(toConvert.getName(),converted.getName());
        assertEquals(toConvert.getPrice(),converted.getPrice());
    }

    @Test
    @Override
    public void canConvertDtoToEntity(){
        ServiceDTO toConvert=new ServiceDTO(Optional.empty(),"Service1",BigDecimal.ONE);
        
        Service converted=service.convertDtoToEntity(toConvert);
        
        assertEquals(toConvert.getName(),converted.getName());
        assertEquals(toConvert.getPrice(),converted.getPrice());
    }

}
