package artifixal.easyservice.controllers;

import artifixal.easyservice.dtos.StatusDTO;
import artifixal.easyservice.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ArtiFixal
 */
@RestController
@RequestMapping(path="/v1/statuses")
@CrossOrigin(origins={"*"})
public class StatusController {

    @Autowired
    public StatusService statusService;
    
    @PostMapping("/addStatus")
    public void addStatus(@RequestBody StatusDTO status){
        statusService.addEntity(status);
    }
    
    @PatchMapping("/editStatus")
    public void editStatus(@RequestBody StatusDTO status){
        statusService.editEntity(status);
    }
    
    @GetMapping("/getAllStatuses")
    public Page<StatusDTO> getAllStatuses(Pageable page) {
        return statusService.getEntitiesDtoPage(page);
    }
    
    @GetMapping("/getStatus/{statusID}")
    public StatusDTO getStatus(@PathVariable Long statusID) {
        return statusService.getEntityDto(statusID);
    }
}
