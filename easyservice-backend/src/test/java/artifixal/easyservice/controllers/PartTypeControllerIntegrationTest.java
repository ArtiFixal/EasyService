package artifixal.easyservice.controllers;

import artifixal.easyservice.commons.integration.IntegrationTest;
import artifixal.easyservice.dtos.PartTypeDTO;
import artifixal.easyservice.entities.PartType;
import artifixal.easyservice.repositories.PartTypeRepository;
import java.util.Optional;
import net.bytebuddy.utility.RandomString;
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
    
    private final int lengthExceedingNameConstriant=PartType.MAX_NAME_LENGTH+1;
    
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
    
    private void testSuccessfulTypeAdd(String typeName){
        PartTypeDTO toAdd=new PartTypeDTO(Optional.empty(),typeName);
        post("/v1/types/addType",HttpStatus.OK)
                .testRowChange(partTypeRepo,1)
                .test(toAdd);
    }
    
    @Test
    public void addType(){
        testSuccessfulTypeAdd("TypeNew");
    }
    
    @Test
    public void addTypeMaxNameLength(){
        testSuccessfulTypeAdd(RandomString.make(PartType.MAX_NAME_LENGTH));
    }
    
    private void testAddFail(HttpStatus expectedStatus,PartTypeDTO request){
        post("/v1/types/addType",expectedStatus)
                .responseBodyNotNull()
                .testRowChange(partTypeRepo,0)
                .test(request);
    }
    
    @Test
    public void shouldNotAddTypeBlankName(){
        testAddFail(HttpStatus.BAD_REQUEST,
                new PartTypeDTO(
                        Optional.empty(),
                        "\t  \n \r \b  "
                )
        );
    }
    
    @Test
    public void shouldNotAddTypeNameExceedsLengthConstraint(){
        testAddFail(HttpStatus.BAD_REQUEST,
                new PartTypeDTO(
                        Optional.empty(),
                        RandomString.make(lengthExceedingNameConstriant)
                )
        );
    }
    
    private void testSuccessfulEdit(String newTypeName){
        PartType type=insertDummyPartType();
        PartTypeDTO toEdit=new PartTypeDTO(Optional.of(type.getId()),newTypeName);
        
        patch("/v1/types/editType",HttpStatus.OK)
                .testRowChange(partTypeRepo,0)
                .test(toEdit);
        
        type=partTypeRepo.findById(type.getId()).get();
        
        assertEquals(toEdit.getId().get(),type.getId());
        assertEquals(toEdit.getName(),type.getName());
    }
    
    @Test
    public void editType(){
        testSuccessfulEdit("TypeEdited");
    }
    
    @Test
    public void editTypeMaxNameLength(){
        testSuccessfulEdit(RandomString.make(PartType.MAX_NAME_LENGTH));
    }
    
    private void testEditFail(HttpStatus expectedStatus,PartTypeDTO request){
        patch("/v1/types/editType",expectedStatus)
                .testRowChange(partTypeRepo,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    @Test
    public void shoudlNotEditTypeNoID(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new PartTypeDTO(
                        Optional.empty(),
                        "TypeNoID"
                )
        );
    }
    
    @Test
    public void shouldNotEditTypeNonexistentID(){
        testEditFail(HttpStatus.NOT_FOUND,
                new PartTypeDTO(
                        Optional.of(1000000l),
                        "TypeNonExist")
        );
    }
    
    @Test
    public void shouldNotEditTypeBlankName(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new PartTypeDTO(
                        Optional.of(insertDummyPartType().getId()),
                        "\n \r \t \b "
                )
        );
    }
    
    @Test
    public void shouldNotEditTypeNameExceedsLengthConstraint(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new PartTypeDTO(
                        Optional.of(insertDummyPartType().getId()),
                        RandomString.make(lengthExceedingNameConstriant)
                )
        );
    }
    
    private PartType insertDummyPartType(){
        PartType toInsert=new PartType(0l,"Type1");
        return partTypeRepo.save(toInsert);
    }
}
