package artifixal.easyservice.controllers;

import artifixal.easyservice.commons.integration.IntegrationTest;
import artifixal.easyservice.dtos.ManufacturerDTO;
import artifixal.easyservice.entities.Manufacturer;
import artifixal.easyservice.repositories.ManufacturerRepository;
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
public class ManufacturerControllerIntegrationTest extends IntegrationTest{
    
    @Autowired
    private ManufacturerRepository manufacturerRepo;
    
    private final int lengthExceedingNameConstraint=Manufacturer.MAX_NAME_LENGTH+1;
    
    @Test
    public void getManufacturerDto(){
        Manufacturer inserted=insertDummyManufacturer();
        ResponseEntity<ManufacturerDTO> response=get(
                "/v1/manufacturers/getManufacturer/"+inserted.getId(),
                HttpStatus.OK,ManufacturerDTO.class)
                .testRowChange(manufacturerRepo,0)
                .responseBodyNotNull()
                .test();
        assertEquals(inserted.getId(),response.getBody().getId().get());
        assertEquals(inserted.getName(),response.getBody().getName());
    }
    
    @Test
    public void shouldNotGetManufacturerNonexistingID(){
        get("/v1/manufacturers/getManufacturer/1000000",HttpStatus.NOT_FOUND)
                .testRowChange(manufacturerRepo,0)
                .responseBodyNotNull()
                .test();
    }
    
    private void testSuccessfulManufactrerAdd(String manufacturerName){
        ManufacturerDTO toInsert=new ManufacturerDTO(Optional.empty(),
                manufacturerName);
        post("/v1/manufacturers/addManufacturer",HttpStatus.OK)
                .testRowChange(manufacturerRepo,1)
                .test(toInsert);
    }
    
    @Test
    public void addManufacturer(){
        testSuccessfulManufactrerAdd("ManInsert");
    }
    
    @Test
    public void addManufacturerMaxNameLength(){
        testSuccessfulManufactrerAdd(RandomString
                .make(Manufacturer.MAX_NAME_LENGTH));
    }
    
    private void testBadRequestManufacturerAdd(String manufacturerName){
        ManufacturerDTO toInsert=new ManufacturerDTO(Optional.empty(),
                manufacturerName);
        post("/v1/manufacturers/addManufacturer",HttpStatus.BAD_REQUEST)
                .testRowChange(manufacturerRepo,0)
                .responseBodyNotNull()
                .test(toInsert);
    }
    
    @Test
    public void shouldNotAddManufacturerBlankName(){
        testBadRequestManufacturerAdd(" \t \n \r \b ");
    }
    
    @Test
    public void shouldNotAddManufacturerNameExceedsLengthConstraint(){
        testBadRequestManufacturerAdd(RandomString
                .make(lengthExceedingNameConstraint));
    }
    
    private void testSuccessfulManufacturerEdit(String manufacturerName){
        Manufacturer man=insertDummyManufacturer();
        ManufacturerDTO toEdit=new ManufacturerDTO(Optional.of(man.getId()),
                manufacturerName);
        
        patch("/v1/manufacturers/editManufacturer",HttpStatus.OK)
                .testRowChange(manufacturerRepo,0)
                .test(toEdit);
        
        man=manufacturerRepo.findById(man.getId()).get();
        
        assertEquals(toEdit.getId().get(),man.getId());
        assertEquals(toEdit.getName(),man.getName());
    }
    
    @Test
    public void editManufacturer(){
        testSuccessfulManufacturerEdit("ManEdited");
    }
    
    @Test
    public void editManufacturerMaxNameLength(){
        testSuccessfulManufacturerEdit(RandomString
                .make(Manufacturer.MAX_NAME_LENGTH));
    }
    
    private void testEditFail(HttpStatus expectedStatus,ManufacturerDTO request){
        patch("/v1/manufacturers/editManufacturer",expectedStatus)
                .testRowChange(manufacturerRepo,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    @Test
    public void shouldNotEditManufacturerNoID(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new ManufacturerDTO(
                        Optional.empty(),
                        "ManNoID")
        );
    }
    
    @Test
    public void shouldNotEditManufacturerNonexistingID(){
        testEditFail(HttpStatus.NOT_FOUND,
                new ManufacturerDTO(
                        Optional.of(10000l),
                        "ManNotFound"
                )
        );
    }
    
    @Test
    public void shouldNotEditManufacturerBlankName(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new ManufacturerDTO(
                        Optional.of(insertDummyManufacturer().getId()),
                        " \t \r \n \b \t "
                )
        );
    }
    
    @Test
    public void shouldNotEditManufacturerNameExceedsLengthConstraint(){
        testEditFail(HttpStatus.BAD_REQUEST,new ManufacturerDTO(
                Optional.of(insertDummyManufacturer().getId()),
                RandomString.make(lengthExceedingNameConstraint))
        );
    }
    
    private Manufacturer insertDummyManufacturer(){
        Manufacturer toInsert=new Manufacturer(0l,"Man2");
        return manufacturerRepo.save(toInsert);
    }
}
