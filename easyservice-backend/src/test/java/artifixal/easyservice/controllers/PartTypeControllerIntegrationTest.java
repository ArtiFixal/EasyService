package artifixal.easyservice.controllers;

import artifixal.easyservice.commons.integration.IntegrationTest;
import artifixal.easyservice.dtos.PartTypeDTO;
import artifixal.easyservice.entities.PartType;
import artifixal.easyservice.repositories.PartTypeRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

/**
 *
 * @author ArtiFixal
 */
@DirtiesContext
public class PartTypeControllerIntegrationTest extends IntegrationTest{

    @Autowired
    private PartTypeRepository partTypeRepo;
    
    @Test
    public void getTypeDto(){
        PartType inserted=insertDummyPartType();
        ResponseEntity<PartTypeDTO> response=get(
                "/v1/types/getType/"+inserted.getId(),HttpStatus.OK,
                PartTypeDTO.class)
                .testRowChange(partTypeRepo,0)
                .responseBodyNotNull()
                .test();
        assertEquals(inserted.getId(),response.getBody().getId().get());
        assertEquals(inserted.getName(),response.getBody().getName());
    }
    
    @Test
    public void shouldNotGetTypeNonexistingID(){
        get("/v1/types/getType/1000000",HttpStatus.NOT_FOUND)
                .testRowChange(partTypeRepo,0)
                .responseBodyNotNull()
                .test();
    }
    
    @Test
    public void addType(){
        PartTypeDTO toAdd=new PartTypeDTO(Optional.empty(),"TypeNew");
        post("/v1/types/addType",HttpStatus.OK)
                .testRowChange(partTypeRepo,1)
                .test(toAdd);
    }
    
    @Test
    public void shouldNotAddTypeBlankName(){
        PartTypeDTO toAdd=new PartTypeDTO(Optional.empty(),"\t  \n \r \b  ");
        post("/v1/types/addType",HttpStatus.BAD_REQUEST)
                .responseBodyNotNull()
                .testRowChange(partTypeRepo,0)
                .test(toAdd);
    }
    
    @Test
    public void editType(){
        PartType type=insertDummyPartType();
        PartTypeDTO toEdit=new PartTypeDTO(Optional.of(type.getId()),"TypeEdited");
        
        patch("/v1/types/editType",HttpStatus.OK)
                .testRowChange(partTypeRepo,0)
                .test(toEdit);
        
        type=partTypeRepo.findById(type.getId()).get();
        
        assertEquals(toEdit.getId().get(),type.getId());
        assertEquals(toEdit.getName(),type.getName());
    }
    
    @Test
    public void shoudlNotEditTypeNoID(){
        testPatchForBadRequestAndNoRowChangeAndResponseBody("/v1/types/editType",
                new PartTypeDTO(
                        Optional.empty(),
                        "TypeNoID"
                )
        );
    }
    
    @Test
    public void shouldNotEditTypeNonexistentID(){
        patch("/v1/types/editType",HttpStatus.NOT_FOUND)
                .responseBodyNotNull()
                .testRowChange(partTypeRepo,0)
                .test(new PartTypeDTO(Optional.of(1000000l),"TypeNonExist"));
    }
    
    @Test
    public void shouldNotEditTypeBlankName(){
        testPatchForBadRequestAndNoRowChangeAndResponseBody("/v1/types/editType",
                new PartTypeDTO(
                        Optional.of(insertDummyPartType().getId()),
                        "\n \r \t \b "
                )
        );
    }
    
    private void testPatchForBadRequestAndNoRowChangeAndResponseBody(String mappingPath,PartTypeDTO request){
        patch(mappingPath,HttpStatus.BAD_REQUEST)
                .testRowChange(partTypeRepo,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    private PartType insertDummyPartType(){
        PartType toInsert=new PartType(0l,"Type1");
        return partTypeRepo.save(toInsert);
    }
}
