package artifixal.easyservice.controllers;

import artifixal.easyservice.commons.integration.IntegrationTest;
import artifixal.easyservice.dtos.DeviceDTO;
import artifixal.easyservice.entities.Device;
import artifixal.easyservice.entities.Manufacturer;
import artifixal.easyservice.repositories.DeviceRepository;
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
        ResponseEntity<DeviceDTO> response=
                get("/v1/devices/getDevice/"+toInsert.getId(),HttpStatus.OK,
                        DeviceDTO.class)
                .test();
        assertEquals(toInsert.getId(),response.getBody().getId().get());
        assertEquals(toInsert.getName(),response.getBody().getName());
        assertEquals(toInsert.getSerialNumber(),response.getBody()
                .getSerialNumber());
    }
    
    @Test
    public void shouldNotGetDeviceDtoNonexistingID(){
        get("/v1/devices/getDevice/10000",HttpStatus.NOT_FOUND)
                // Should return error.
                .responseBodyNotNull()
                .test();
    }
    
    @Test
    public void addDevice(){
        post("/v1/devices/addDevice",HttpStatus.OK)
                .testRowChange(deviceRepo,1)
                .test(
                        new DeviceDTO(
                            Optional.empty(),
                            insertDummyManufacturer().getId(),
                            "Good PC 1",
                            "GPC111"
                        )
                );
    }
    
    @Test
    public void shouldNotAddDeviceNonexistentManufacturer(){
        post("/v1/devices/addDevice",HttpStatus.NOT_FOUND)
                .testRowChange(deviceRepo,0)
                .responseBodyNotNull()
                .test(
                        new DeviceDTO(
                            Optional.empty(),
                            100000,
                            "Good PC 2",
                            "GPC222"
                        )
                );
    }
    
    @Test
    public void shouldNotAddDeviceBlankName(){
        testPostForBadRequestAndNoRowCountChangeAndResponseBody("/v1/devices/addDevice",
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
        testPostForBadRequestAndNoRowCountChangeAndResponseBody("/v1/devices/addDevice",
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
        
        put("/v1/devices/editDevice",HttpStatus.OK)
                .testRowChange(deviceRepo,0)
                .responseBodyNotNull()
                .test(dataToUpdate);
        // Update device reference
        device=deviceRepo.findById(device.getId()).get();
        
        assertEquals(dataToUpdate.getManufacturerID(),device.getManufacturer()
                .getId(),"Manufacturer have not changed");
        assertEquals(dataToUpdate.getName(),device.getName());
        assertEquals(dataToUpdate.getSerialNumber(),device.getSerialNumber());
    }
    
    @Test
    public void shouldNotEditDeviceNoDtoID(){
        testPutForBadRequestAndNoRowCountChangeAndResponseBody("/v1/devices/editDevice",
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
        testPutForNotFoundAndNoRowCountChangeAndResponseBody("/v1/devices/editDevice",
                new DeviceDTO(
                        Optional.of(100000l),
                        insertDummyManufacturer().getId(),
                        "GoodPC Nonexistent ID",
                        "GPCNEX"
                )
        );
    }
    
    @Test
    public void shouldNotEditDeviceNonexistentManufacturer(){
        testPutForNotFoundAndNoRowCountChangeAndResponseBody("/v1/devices/editDevice",
                new DeviceDTO(
                        Optional.of(insertDummyDevice().getId()),
                        100000,
                        "GoodPC no manufacturer",
                        "GPCNMA"
                )
        );
    }
    
    @Test
    public void shouldNotEditDeviceBlankName(){
        testPutForBadRequestAndNoRowCountChangeAndResponseBody("/v1/devices/editDevice",
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
        testPutForBadRequestAndNoRowCountChangeAndResponseBody("/v1/devices/editDevice",
                new DeviceDTO(
                        Optional.empty(),
                        insertDummyManufacturer().getId(),
                        "GoodPC blank serial",
                        " \t \n \r \b "
                )
        );
    }
    
    private void testPostForBadRequestAndNoRowCountChangeAndResponseBody(String mappingPath,DeviceDTO device){
        post(mappingPath,HttpStatus.BAD_REQUEST)
                .testRowChange(deviceRepo,0)
                .responseBodyNotNull()
                .test(device);
    }
    
    private void testPutForBadRequestAndNoRowCountChangeAndResponseBody(String mappingPath,DeviceDTO device){
        put(mappingPath,HttpStatus.BAD_REQUEST)
                .testRowChange(deviceRepo,0)
                .responseBodyNotNull()
                .test(device);
    }
    
    private void testPutForNotFoundAndNoRowCountChangeAndResponseBody(String mappingPath,DeviceDTO device){
        put(mappingPath,HttpStatus.NOT_FOUND)
                .testRowChange(deviceRepo,0)
                .responseBodyNotNull()
                .test(device);
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
