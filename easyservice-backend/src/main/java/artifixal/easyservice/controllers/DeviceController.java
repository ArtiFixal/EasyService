package artifixal.easyservice.controllers;

import artifixal.easyservice.dtos.DeviceDTO;
import artifixal.easyservice.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ArtiFixal
 */
@RestController
@RequestMapping(path="/v1/devices")
@CrossOrigin(origins={"*"})
public class DeviceController{

    @Autowired
    private DeviceService deviceService;
    
    @PostMapping("/addDevice")
    public void addDevice(@RequestBody DeviceDTO device){
        deviceService.addEntity(device);
    }

    @PutMapping("/editDevice")
    public void editDevice(@RequestBody DeviceDTO device){
        deviceService.editEntity(device);
    }

    @GetMapping("/getAllDevices")
    public Page<DeviceDTO> getAllDevices(Pageable page){
        return deviceService.getEntitiesDtoPage(page);
    }

    @GetMapping("/getDevice/{deviceID}")
    public DeviceDTO getDevice(@PathVariable Long deviceID){
        return deviceService.getEntityDto(deviceID);
    }
}
