package artifixal.easyservice.controllers;

import artifixal.easyservice.commons.integration.IntegrationTest;
import artifixal.easyservice.dtos.StatusDTO;
import artifixal.easyservice.entities.Status;
import artifixal.easyservice.repositories.StatusRepository;
import java.util.Optional;
import net.bytebuddy.utility.RandomString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

/**
 *
 * @author ArtiFixal
 */
@DirtiesContext
public class StatusControllerIntegrationTest extends IntegrationTest{

    @Autowired
    private StatusRepository statusRepository;
    
    private final int lengthExceedingConstraint=Status.MAX_NAME_LENGTH+1;
    
    @Test
    public void getStatus(){
        get("/v1/statuses/getStatus/"+insertDummyStatus().getId(),
                HttpStatus.OK,StatusDTO.class)
                .testRowChange(statusRepository,0)
                .test();
    }
    
    @Test
    public void shouldNotGetStatusNonexistingID(){
        get("/v1/statuses/getStatus/1000000",HttpStatus.NOT_FOUND,
                StatusDTO.class)
                .testRowChange(statusRepository,0)
                .responseBodyNotNull()
                .test();
    }
    
    private void testSuccessfulAdd(String statusName){
        StatusDTO request=new StatusDTO(Optional.empty(),statusName);
        post("/v1/statuses/addStatus",HttpStatus.OK)
                .testRowChange(statusRepository,1)
                .test(request);
    }
    
    @Test
    public void addStatus(){
        testSuccessfulAdd("Status2");
    }
    
    @Test
    public void addStatusMaxNameLength(){
        testSuccessfulAdd(RandomString.make(Status.MAX_NAME_LENGTH));
    }
    
    private void testAddFail(HttpStatus expectedStatus,StatusDTO request){
        post("/v1/statuses/addStatus",expectedStatus)
                .testRowChange(statusRepository,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    @Test
    public void shouldNotAddStatusBlankName(){
        testAddFail(HttpStatus.BAD_REQUEST,
                new StatusDTO(
                        Optional.empty(),
                        "\b\t\r\n"
                )
        );
    }
    
    @Test
    public void shouldNotAddStatusNameExceedsLengthLimit(){
        testAddFail(HttpStatus.BAD_REQUEST,
                new StatusDTO(
                        Optional.empty(),
                        RandomString.make(lengthExceedingConstraint)
                )
        );
    }
    
    private void testSuccessfulEdit(String newStatusName){
        Status status=insertDummyStatus();
        final StatusDTO toEdit=new StatusDTO(Optional.of(status.getId()),
                newStatusName);
        patch("/v1/statuses/editStatus",HttpStatus.OK)
                .testRowChange(statusRepository,0)
                .test(toEdit);
        
        status=statusRepository.findById(status.getId()).get();
        
        assertEquals(toEdit.getName(),status.getName());
    }
    
    @Test
    public void editStatus(){
        testSuccessfulEdit("StatusEdited");
    }
    
    @Test
    public void editStatusMaxNameLength(){
        testSuccessfulEdit(RandomString.make(Status.MAX_NAME_LENGTH));
    }
    
    private void testEditFail(HttpStatus expectedStatus,StatusDTO request){
        patch("/v1/statuses/editStatus",expectedStatus)
                .testRowChange(statusRepository,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    @Test
    public void shouldNotEditStausNoID(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new StatusDTO(
                        Optional.empty(),
                        "StatusNoID"
                )
        );
    }
    
    @Test
    public void shouldNotEditStatusNonexistingID(){
        testEditFail(HttpStatus.NOT_FOUND,
                new StatusDTO(
                        Optional.of(100000l),
                        "StatusNonExisting"
                )
        );
    }
    
    @Test
    public void shouldNotEditStatusBlankName(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new StatusDTO(
                        Optional.of(insertDummyStatus().getId()),
                        " \n\r \t\b"
                )
        );
    }
    
    @Test
    public void shouldNotEditStatusNameExceedsLengthConstraint(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new StatusDTO(
                        Optional.of(insertDummyStatus().getId()),
                        RandomString.make(lengthExceedingConstraint)
                )
        );
    }
    
    private Status insertDummyStatus(){
        Status toInsert=new Status(0l,"Status1");
        return statusRepository.save(toInsert);
    }
}
