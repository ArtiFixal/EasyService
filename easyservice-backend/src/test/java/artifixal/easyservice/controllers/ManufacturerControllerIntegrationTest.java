package artifixal.easyservice.controllers;

import artifixal.easyservice.commons.integration.IntegrationTest;
import artifixal.easyservice.dtos.ManufacturerDTO;
import artifixal.easyservice.entities.Manufacturer;
import artifixal.easyservice.repositories.ManufacturerRepository;
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
public class ManufacturerControllerIntegrationTest extends IntegrationTest{
    
    @Autowired
    private ManufacturerRepository manufacturerRepo;
    
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
    
    @Test
    public void addManufacturer(){
        ManufacturerDTO toInsert=new ManufacturerDTO(Optional.empty(),
                "ManInsert");
        post("/v1/manufacturers/addManufacturer",HttpStatus.OK)
                .testRowChange(manufacturerRepo,1)
                .test(toInsert);
    }
    
    @Test
    public void shouldNotAddManufacturerBlankName(){
        ManufacturerDTO toInsert=new ManufacturerDTO(Optional.empty(),
                " \t \n \r \b ");
        post("/v1/manufacturers/addManufacturer",HttpStatus.BAD_REQUEST)
                .testRowChange(manufacturerRepo,0)
                .responseBodyNotNull()
                .test(toInsert);
    }
    
    @Test
    public void editManufacturer(){
        Manufacturer man=insertDummyManufacturer();
        ManufacturerDTO toEdit=new ManufacturerDTO(Optional.of(man.getId()),"ManEdited");
        
        patch("/v1/manufacturers/editManufacturer",HttpStatus.OK)
                .testRowChange(manufacturerRepo,0)
                .test(toEdit);
        
        man=manufacturerRepo.findById(man.getId()).get();
        
        assertEquals(toEdit.getId().get(),man.getId());
        assertEquals(toEdit.getName(),man.getName());
    }
    
    @Test
    public void shouldNotEditManufacturerNoID(){
        ManufacturerDTO toEdit=new ManufacturerDTO(Optional.empty(),"ManNoID");
        patch("/v1/manufacturers/editManufacturer",HttpStatus.BAD_REQUEST)
                .testRowChange(manufacturerRepo,0)
                .responseBodyNotNull()
                .test(toEdit);
    }
    
    @Test
    public void shouldNotEditManufacturerNonexistingID(){
        // FIX: RestMethodRequest response type, recommit
        ManufacturerDTO toEdit=new ManufacturerDTO(Optional.of(10000l),"ManNotFound");
        patch("/v1/manufacturers/editManufacturer",HttpStatus.NOT_FOUND)
                .testRowChange(manufacturerRepo,0)
                .responseBodyNotNull()
                .test(toEdit);
    }
    
    @Test
    public void shouldNotEditManufacturerBlankName(){
        ManufacturerDTO toEdit=new ManufacturerDTO(
                Optional.of(insertDummyManufacturer().getId())," \t \r \n \b \t ");
        patch("/v1/manufacturers/editManufacturer",HttpStatus.BAD_REQUEST)
                .testRowChange(manufacturerRepo,0)
                .responseBodyNotNull()
                .test(toEdit);
    }
    
    private Manufacturer insertDummyManufacturer(){
        Manufacturer toInsert=new Manufacturer(0l,"Man2");
        return manufacturerRepo.save(toInsert);
    }
}
