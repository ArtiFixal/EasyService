package artifixal.easyservice.controllers;

import artifixal.easyservice.commons.integration.IntegrationTest;
import artifixal.easyservice.dtos.ServiceDTO;
import artifixal.easyservice.entities.Service;
import artifixal.easyservice.repositories.ServiceRepository;
import java.math.BigDecimal;
import java.util.Optional;
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
    
    @Test
    public void addService(){
        ServiceDTO toInsert=new ServiceDTO(Optional.empty(),"Servcie2",
                BigDecimal.ONE);
        post("/v1/services/addService",HttpStatus.OK)
                .testRowChange(serviceRepo,1)
                .test(toInsert);
    }
    
    @Test
    public void shouldNotAddServiceBlankName(){
        testPostForBadRequestAndNoRowChangeAndResponseBody("/v1/services/addService",
                new ServiceDTO(
                        Optional.empty(),
                        "\b \r \t \n",
                        BigDecimal.ONE
                )
        );
    }
    
    @Test
    public void shouldNotAddServiceNegativePrice(){
        testPostForBadRequestAndNoRowChangeAndResponseBody("/v1/services/addService",
                new ServiceDTO(
                        Optional.of(insertDummyService().getId()),
                        "ServiceNegative",
                        BigDecimal.valueOf(-1)
                )
        );
    }
    
    @Test
    public void editService(){
        ServiceDTO toEdit=new ServiceDTO(Optional.of(insertDummyService().getId()),
                "ServiceEdited",BigDecimal.TEN);
        put("/v1/services/editService",HttpStatus.OK)
                .testRowChange(serviceRepo,0)
                .test(toEdit);
    }
    
    @Test
    public void shouldNotEditServiceNoID(){
        testPutForBadRequestAndNoRowChangeAndResponseBody("/v1/services/editService",
                new ServiceDTO(
                        Optional.empty(),
                        "ServiceNoID",
                        BigDecimal.ONE
                )
        );
    }
    
    @Test
    public void shouldNotEditServiceNonexistingID(){
        ServiceDTO toEdit=new ServiceDTO(Optional.of(1000000l),
                "ServiceNonExisting",BigDecimal.ONE);
        put("/v1/services/editService",HttpStatus.NOT_FOUND)
                .testRowChange(serviceRepo,0)
                .responseBodyNotNull()
                .test(toEdit);
    }
    
    @Test
    public void shouldNotEditServiceBlankName(){
        testPutForBadRequestAndNoRowChangeAndResponseBody("/v1/services/editService",
                new ServiceDTO(
                        Optional.of(insertDummyService().getId()),
                        "\t \b \n\r",
                        BigDecimal.TEN
                )
        );
    }
    
    @Test
    public void shouldNotEditServiceNegativePrice(){
        testPutForBadRequestAndNoRowChangeAndResponseBody("/v1/services/editService",
                new ServiceDTO(
                        Optional.of(insertDummyService().getId()),
                        "ServiceNegative",
                        BigDecimal.valueOf(-1)
                )
        );
    }
    
    private void testPutForBadRequestAndNoRowChangeAndResponseBody(String mappingPath,ServiceDTO request){
        put(mappingPath,HttpStatus.BAD_REQUEST)
                .testRowChange(serviceRepo,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    private void testPostForBadRequestAndNoRowChangeAndResponseBody(String mappingPath,ServiceDTO request){
        post(mappingPath,HttpStatus.BAD_REQUEST)
                .testRowChange(serviceRepo,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    private Service insertDummyService(){
        Service toInsert=new Service(0l,"Service1",BigDecimal.ONE);
        return serviceRepo.save(toInsert);
    }
}
