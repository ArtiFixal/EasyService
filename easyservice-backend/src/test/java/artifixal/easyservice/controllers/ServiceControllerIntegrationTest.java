package artifixal.easyservice.controllers;

import artifixal.easyservice.commons.integration.IntegrationTest;
import artifixal.easyservice.dtos.ServiceDTO;
import artifixal.easyservice.entities.Service;
import artifixal.easyservice.repositories.ServiceRepository;
import java.math.BigDecimal;
import java.util.Optional;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

/**
 *
 * @author ArtiFixal
 */
@DirtiesContext
public class ServiceControllerIntegrationTest extends IntegrationTest{

    @Autowired
    private ServiceRepository serviceRepo;
    
    private final int lengthExceedingNameConstraint=Service.MAX_NAME_LENGTH+1;
    
    @Test
    public void getServiceDto(){
        get("/v1/services/getService/"+insertDummyService().getId(),
                HttpStatus.OK,ServiceDTO.class)
                .testRowChange(serviceRepo,0)
                .test();
    }
    
    @Test
    public void shouldNotGetServiceDtoNonexistingID(){
        get("/v1/services/getService/1000000",HttpStatus.NOT_FOUND,
                ServiceDTO.class)
                .testRowChange(serviceRepo,0)
                .responseBodyNotNull()
                .test();
    }
    
    private void testSuccessfulAdd(String serviceName){
        ServiceDTO toInsert=new ServiceDTO(Optional.empty(),serviceName,
                BigDecimal.ONE);
        post("/v1/services/addService",HttpStatus.OK)
                .testRowChange(serviceRepo,1)
                .test(toInsert);
    }
    
    @Test
    public void addService(){
        testSuccessfulAdd("Servcie2");
    }
    
    @Test
    public void addServiceMaxNameLength(){
        testSuccessfulAdd(RandomString.make(Service.MAX_NAME_LENGTH));
    }
    
    private void testAddFail(HttpStatus exceptedStatus,ServiceDTO request){
        post("/v1/services/addService",exceptedStatus)
                .testRowChange(serviceRepo,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    @Test
    public void shouldNotAddServiceBlankName(){
        testAddFail(HttpStatus.BAD_REQUEST,
                new ServiceDTO(
                        Optional.empty(),
                        "\b \r \t \n",
                        BigDecimal.ONE
                )
        );
    }
    
    @Test
    public void shouldNotAddServiceNameExceedsLengthConstraint(){
        testAddFail(HttpStatus.BAD_REQUEST,
                new ServiceDTO(
                        Optional.empty(),
                        RandomString.make(lengthExceedingNameConstraint),
                        BigDecimal.ONE
                )
        );
    }
    
    @Test
    public void shouldNotAddServiceNegativePrice(){
        testAddFail(HttpStatus.BAD_REQUEST,
                new ServiceDTO(
                        Optional.of(insertDummyService().getId()),
                        "ServiceNegative",
                        BigDecimal.valueOf(-1)
                )
        );
    }
    
    private void testSuccessfulEdit(String newServiceName){
        ServiceDTO toEdit=new ServiceDTO(Optional.of(insertDummyService().getId()),
                newServiceName,BigDecimal.TEN);
        put("/v1/services/editService",HttpStatus.OK)
                .testRowChange(serviceRepo,0)
                .test(toEdit);
    }
    
    @Test
    public void editService(){
        testSuccessfulEdit("ServiceEdited");
    }
    
    @Test
    public void editServiceMaxNameLength(){
        testSuccessfulEdit(RandomString.make(Service.MAX_NAME_LENGTH));
    }
    
    private void testEditFail(HttpStatus expectedStatus,ServiceDTO request){
        put("/v1/services/editService",expectedStatus)
                .testRowChange(serviceRepo,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    @Test
    public void shouldNotEditServiceNoID(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new ServiceDTO(
                        Optional.empty(),
                        "ServiceNoID",
                        BigDecimal.ONE
                )
        );
    }
    
    @Test
    public void shouldNotEditServiceNonexistingID(){
        testEditFail(HttpStatus.NOT_FOUND,
                new ServiceDTO(
                        Optional.of(1000000l),
                        "ServiceNonExisting",
                        BigDecimal.ONE
                )
        );
    }
    
    @Test
    public void shouldNotEditServiceBlankName(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new ServiceDTO(
                        Optional.of(insertDummyService().getId()),
                        "\t \b \n\r",
                        BigDecimal.ONE
                )
        );
    }
    
    @Test
    public void shouldNotEditServiceNameExceedsLengthConstraint(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new ServiceDTO(
                        Optional.of(insertDummyService().getId()),
                        RandomString.make(lengthExceedingNameConstraint),
                        BigDecimal.ONE
                )
        );
    }
    
    @Test
    public void shouldNotEditServiceNegativePrice(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new ServiceDTO(
                        Optional.of(insertDummyService().getId()),
                        "ServiceNegative",
                        BigDecimal.valueOf(-1)
                )
        );
    }
    
    private Service insertDummyService(){
        Service toInsert=new Service(0l,"Service1",BigDecimal.ONE);
        return serviceRepo.save(toInsert);
    }
}
