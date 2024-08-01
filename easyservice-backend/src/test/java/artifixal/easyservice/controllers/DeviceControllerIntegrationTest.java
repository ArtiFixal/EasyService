package artifixal.easyservice.controllers;

import artifixal.easyservice.commons.IntegrationTest;
import artifixal.easyservice.dtos.DeviceDTO;
import artifixal.easyservice.entities.Device;
import artifixal.easyservice.entities.Manufacturer;
import artifixal.easyservice.repositories.DeviceRepository;
import artifixal.easyservice.repositories.ManufacturerRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
public class DeviceControllerIntegrationTest extends IntegrationTest{
    
    @Autowired
    private ManufacturerRepository manufacturerRepo;
    
    @Autowired
    private DeviceRepository deviceRepo;
    
    @Test
    public void getDeviceDto(){
        Device toInsert=new Device(0l,insertDummyManufacturer(),"GoodPC Get",
                "GPCGET");
        toInsert=deviceRepo.save(toInsert);
        ResponseEntity<DeviceDTO> response=getAndTest(
                "/v1/devices/getDevice/"+toInsert.getId(),DeviceDTO.class,
                HttpStatus.OK);
        assertEquals(toInsert.getId(),response.getBody().getId().get());
        assertEquals(toInsert.getName(),response.getBody().getName());
        assertEquals(toInsert.getSerialNumber(),response.getBody()
                .getSerialNumber());
    }
    
    @Test
    public void shouldNotGetDeviceDtoNonexistingID(){
        Object responseBody=getAndTest("/v1/devices/getDevice/10000",
                String.class,HttpStatus.NOT_FOUND).getBody();
        assertNotNull(responseBody);
    }
    
    @Test
    public void addDevice(){
        long manufacturerID=insertDummyManufacturer().getId();
        testRowCountAfterRequest(()->postAndTest("/v1/devices/addDevice",
                new DeviceDTO(
                        Optional.empty(),
                        manufacturerID,
                        "Good PC 1",
                        "GPC111"
                ),
                HttpStatus.OK),deviceRepo,1);
    }
    
    @Test
    public void shouldNotAddDeviceNonexistentManufacturer(){
        testRowCountAfterRequest(()->postAndTest("/v1/devices/addDevice",
                new DeviceDTO(
                        Optional.empty(),
                        100000,
                        "Good PC 2",
                        "GPC222"
                ),
                HttpStatus.NOT_FOUND),deviceRepo,0,testForNotNullResponseBody());
    }
    
    @Test
    public void shouldNotAddDeviceBlankName(){
        testPostForBadRequestAndNoRowCountChange("/v1/devices/addDevice",
                new DeviceDTO(
                        Optional.empty(),
                        insertDummyManufacturer().getId(),
                        "\t \n \r \b",
                        "GPC333"
                )
        );
    }
    
    @Test
    public void shouldNotAddDeviceBlankSerialNumber(){
        testPostForBadRequestAndNoRowCountChange("/v1/devices/addDevice",
                new DeviceDTO(
                        Optional.empty(),
                        insertDummyManufacturer().getId(),
                        "Good PC 4",
                        "\t \n \r \b"
                )
        );
    }
    
    @Test
    public void editDevice(){
        Device device=insertDummyDevice();
        Manufacturer newMan=new Manufacturer(0l,"manE");
        newMan=manufacturerRepo.save(newMan);
        final DeviceDTO dataToUpdate=new DeviceDTO(Optional.of(device.getId()),
                newMan.getId(),"GoodPC edited","GPCEEE");
        
        testRowCountAfterRequest(()->putAndTest("/v1/devices/editDevice",
                dataToUpdate,HttpStatus.OK),deviceRepo,0);
        // Update device reference
        device=deviceRepo.findById(device.getId()).get();
        
        assertEquals(dataToUpdate.getManufacturerID(),device.getManufacturer()
                .getId(),"Manufacturer didn't changed");
        assertEquals(dataToUpdate.getName(),device.getName());
        assertEquals(dataToUpdate.getSerialNumber(),device.getSerialNumber());
    }
    
    @Test
    public void shouldNotEditDeviceNoDtoID(){
        testPutForBadRequestAndNoRowCountChange("/v1/devices/editDevice",
                new DeviceDTO(
                        Optional.empty(),
                        insertDummyManufacturer().getId(),
                        "GoodPC No ID",
                        "GPCNID"
                )
        );
    }
    
    @Test
    public void shouldNotEditDeviceNonexistentID(){
        testPutForNotFoundAndNoRowCountChange("/v1/devices/editDevice",
                new DeviceDTO(
                        Optional.of(100000l),
                        insertDummyManufacturer().getId(),
                        "GoodPC Nonexistent ID",
                        "GPCNEX")
        );
    }
    
    @Test
    public void shouldNotEditDeviceNonexistentManufacturer(){
        testPutForBadRequestAndNoRowCountChange("/v1/devices/editDevice",
                new DeviceDTO(
                        Optional.empty(),
                        100000,
                        "GoodPC no manufacturer",
                        "GPCNMA"
                )
        );
    }
    
    @Test
    public void shouldNotEditDeviceBlankName(){
        testPutForBadRequestAndNoRowCountChange("/v1/devices/editDevice",
                new DeviceDTO(
                        Optional.empty(),
                        insertDummyManufacturer().getId(),
                        " \t \n \r \b ",
                        "GPCENAME"
                )
        );
    }
    
    @Test
    public void shouldNotEditDeviceBlankSerialNumber(){
        testPutForBadRequestAndNoRowCountChange("/v1/devices/editDevice",
                new DeviceDTO(
                        Optional.empty(),
                        insertDummyManufacturer().getId(),
                        "GoodPC blank serial",
                        " \t \n \r \b "
                )
        );
    }
    
    private void testPostForBadRequestAndNoRowCountChange(String mappingPath,DeviceDTO device){
        testRowCountAfterRequest(()->postAndTest(mappingPath,device,
                HttpStatus.BAD_REQUEST),deviceRepo,0,testForNotNullResponseBody());
    }
    
    private void testPutForBadRequestAndNoRowCountChange(String mappingPath,DeviceDTO device){
        testRowCountAfterRequest(()->putAndTest(mappingPath,device,
                HttpStatus.BAD_REQUEST),deviceRepo,0,testForNotNullResponseBody());
    }
    
    private void testPutForNotFoundAndNoRowCountChange(String mappingPath,DeviceDTO device){
        testRowCountAfterRequest(()->putAndTest(mappingPath,device,
                HttpStatus.NOT_FOUND),deviceRepo,0,testForNotNullResponseBody());
    }
    
    private Manufacturer insertDummyManufacturer(){
        final Manufacturer defManufacturer=new Manufacturer(0l,"man1");
        return manufacturerRepo.save(defManufacturer);
    }
    
    private Device insertDummyDevice(){
        final Manufacturer man=insertDummyManufacturer();
        Device device=new Device(0l,man,"Good PC edit","GPCIDD");
        return deviceRepo.save(device);
    }
}
