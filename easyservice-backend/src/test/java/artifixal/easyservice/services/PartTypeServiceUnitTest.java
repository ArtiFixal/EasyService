package artifixal.easyservice.services;

import artifixal.easyservice.dtos.PartTypeDTO;
import artifixal.easyservice.entities.PartType;
import artifixal.easyservice.repositories.PartTypeRepository;
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
public class PartTypeServiceUnitTest implements ServiceUnitTest{
    
    @Autowired
    @InjectMocks
    private PartTypeService service;
    
    @Mock
    private PartTypeRepository repo;
    
    @BeforeEach
    public void setup(){
        // this.repo is null fix
        ReflectionTestUtils.setField(service,"repo",repo);
    }

    @Test
    @Override
    public void canEditEntity(){
        PartType toEdit=new PartType(1l,"Type1");
        PartTypeDTO newTypeData=new PartTypeDTO(Optional.of(1l),"TypeEdited");
        
        // Stubs
        when(repo.findById(any())).thenReturn(Optional.of(toEdit));
        when(repo.save(any())).thenReturn(toEdit);
        
        service.editEntity(newTypeData);
        
        verify(repo,times(1)).save(any());
        verify(repo,times(1)).findById(any());
        
        assertEquals(newTypeData.getName(),toEdit.getName());
    }

    @Test
    @Override
    public void canConvertEntityToDto(){
        PartType type=new PartType(1l,"Type1");
        
        PartTypeDTO converted=service.convertEntityToDto(type);
        
        assertEquals(type.getId(),converted.getId().get());
        assertEquals(type.getName(),converted.getName());
    }

    @Test
    @Override
    public void canConvertDtoToEntity(){
        PartTypeDTO typeDto=new PartTypeDTO(Optional.empty(),"Type1");
        
        PartType converted=service.convertDtoToEntity(typeDto);
        
        assertEquals(typeDto.getName(),converted.getName());
    }
}
