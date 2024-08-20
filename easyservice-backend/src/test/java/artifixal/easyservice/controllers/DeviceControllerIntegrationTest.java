package artifixal.easyservice.controllers;

import artifixal.easyservice.commons.integration.IntegrationTest;
import artifixal.easyservice.dtos.DeviceDTO;
import artifixal.easyservice.entities.Device;
import artifixal.easyservice.entities.Manufacturer;
import artifixal.easyservice.repositories.DeviceRepository;
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
public class DeviceControllerIntegrationTest extends IntegrationTest{
    
    @Autowired
    private ManufacturerRepository manufacturerRepo;
    
    @Autowired
    private DeviceRepository deviceRepo;
    
    private final int lengthExceedingNameConstraint=Device.MAX_NAME_LENGTH+1;
    private final int lengthExceedingSerialNumberConstraint=Device.MAX_SERIAL_NUMBER_LENGTH+1;
    
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
    
    private void testSuccessfulDeviceAdd(String devicaName,String serialNumber){
        post("/v1/devices/addDevice",HttpStatus.OK)
                .testRowChange(deviceRepo,1)
                .test(
                        new DeviceDTO(
                            Optional.empty(),
                            insertDummyManufacturer().getId(),
                            devicaName,
                            serialNumber
                        )
                );
    }
    
    @Test
    public void addDevice(){
        testSuccessfulDeviceAdd("Good PC 1","GPC111");
    }
    
    @Test
    public void addDeviceMaxNameLength(){
        testSuccessfulDeviceAdd(RandomString.make(Device.MAX_NAME_LENGTH),
                "GPCMAXN");
    }
    
    @Test
    public void addDeviceMaxSerialLength(){
        testSuccessfulDeviceAdd("Good PC MAXSN",
                RandomString.make(Device.MAX_SERIAL_NUMBER_LENGTH));
    }
    
    private void testAddFail(HttpStatus expectedStatus,DeviceDTO request){
        post("/v1/devices/addDevice",expectedStatus)
                .testRowChange(deviceRepo,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    @Test
    public void shouldNotAddDeviceNonexistentManufacturer(){
        testAddFail(HttpStatus.NOT_FOUND,
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
        testAddFail(HttpStatus.BAD_REQUEST,
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
        testAddFail(HttpStatus.BAD_REQUEST,
                new DeviceDTO(
                        Optional.empty(),
                        insertDummyManufacturer().getId(),
                        "Good PC 4",
                        "\t \n \r \b"
                )
        );
    }
    
    @Test
    public void shouldNotAddDeviceNameExceedsLengthConstraint(){
        testAddFail(HttpStatus.BAD_REQUEST,
                new DeviceDTO(
                        Optional.empty(),
                        insertDummyManufacturer().getId(),
                        RandomString.make(lengthExceedingNameConstraint),
                        "GPCLMAX"
                )
        );
    }
    
    @Test
    public void shouldNotAddDeviceSerialExceedsLengthConstraint(){
        testAddFail(HttpStatus.BAD_REQUEST,
                new DeviceDTO(
                        Optional.empty(),
                        insertDummyManufacturer().getId(),
                        "Good PC 5",
                        RandomString.make(lengthExceedingSerialNumberConstraint)
                )
        );
    }
    
    private void testSuccessfulDeviceEdit(String deviceName,String serialNumber){
        Device device=insertDummyDevice();
        Manufacturer newMan=new Manufacturer(0l,"manE");
        newMan=manufacturerRepo.save(newMan);
        final DeviceDTO dataToUpdate=new DeviceDTO(Optional.of(device.getId()),
                newMan.getId(),deviceName,serialNumber);
        
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
    public void editDevice(){
        testSuccessfulDeviceEdit("GoodPC edited","GPCEEE");
    }
    
    @Test
    public void editDeviceMaxNameLength(){
        testSuccessfulDeviceEdit(RandomString.make(Device.MAX_NAME_LENGTH),
                "GPCEMAXN");
    }
    
    @Test
    public void editDeviceMaxServiceNumberLength(){
        testSuccessfulDeviceEdit("GoodPC edited MaxS",
                RandomString.make(Device.MAX_SERIAL_NUMBER_LENGTH));
    }
    
    private void testEditFail(HttpStatus expectedStatus,DeviceDTO request){
        put("/v1/devices/editDevice",expectedStatus)
                .testRowChange(deviceRepo,0)
                .responseBodyNotNull()
                .test(request);
    }
    
    @Test
    public void shouldNotEditDeviceNoDtoID(){
        testEditFail(HttpStatus.BAD_REQUEST,
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
        testEditFail(HttpStatus.NOT_FOUND,
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
        testEditFail(HttpStatus.NOT_FOUND,
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
        testEditFail(HttpStatus.BAD_REQUEST,
                new DeviceDTO(
                        Optional.of(insertDummyDevice().getId()),
                        insertDummyManufacturer().getId(),
                        " \t \n \r \b ",
                        "GPCENAME"
                )
        );
    }
    
    @Test
    public void shouldNotEditDeviceBlankSerialNumber(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new DeviceDTO(
                        Optional.of(insertDummyDevice().getId()),
                        insertDummyManufacturer().getId(),
                        "GoodPC blank serial",
                        " \t \n \r \b "
                )
        );
    }
    
    @Test
    public void shouldNotEditDeviceNameExceedsLengthConstraint(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new DeviceDTO(
                        Optional.of(insertDummyDevice().getId()),
                        insertDummyManufacturer().getId(),
                        RandomString.make(lengthExceedingNameConstraint),
                        "GPCNEC"
                )
        );
    }
    
    @Test
    public void shouldNotEditDeiveSerialNumberExceedsLengthConstrint(){
        testEditFail(HttpStatus.BAD_REQUEST,
                new DeviceDTO(
                        Optional.of(insertDummyDevice().getId()),
                        insertDummyManufacturer().getId(),
                        "GoodPC BadSerialNumber",
                        RandomString.make(lengthExceedingSerialNumberConstraint)
                )
        );
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
