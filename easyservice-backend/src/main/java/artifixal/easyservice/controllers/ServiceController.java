package artifixal.easyservice.controllers;

import artifixal.easyservice.dtos.ServiceDTO;
import artifixal.easyservice.services.ServiceService;
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
@RequestMapping(path="/v1/services")
@CrossOrigin(origins={"*"})
public class ServiceController {
    
    @Autowired
    private ServiceService serviceService;
    
    @PostMapping("/addService")
    public void addService(@RequestBody ServiceDTO service){
        serviceService.addEntity(service);
    }
    
    @PutMapping("/editService")
    public void editService(@RequestBody ServiceDTO service){
        serviceService.editEntity(service);
    }
    
    @GetMapping("/getService/{serviceID}")
    public ServiceDTO getService(@PathVariable Long serviceID){
        return serviceService.getEntityDto(serviceID);
    }
    
    @GetMapping("/getAllServices")
    public Page<ServiceDTO> getAllServices(Pageable page){
        return serviceService.getEntitiesDtoPage(page);
    }
}
