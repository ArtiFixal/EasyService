package artifixal.easyservice.controllers;

import artifixal.easyservice.dtos.ManufacturerDTO;
import artifixal.easyservice.services.ManufacturerService;
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
@RequestMapping(path="/v1/manufacturers")
@CrossOrigin(origins={"*"})
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @PostMapping("/addManufacturer")
    public void addManufacturer(@RequestBody ManufacturerDTO manufacturer) {
        manufacturerService.addEntity(manufacturer);
    }
    
    @PatchMapping("/editManufacturer")
    public void editManufacturer(@RequestBody ManufacturerDTO manufacturerDTO){
        manufacturerService.editEntity(manufacturerDTO);
    }

    @GetMapping("/getAllManufacturers")
    public Page<ManufacturerDTO> getAllManufacturers(Pageable page) {
        return manufacturerService.getEntitiesDtoPage(page);
    }

    @GetMapping("/getManufacturer/{manufacturerID}")
    public ManufacturerDTO getManufacturer(@PathVariable Long manufacturerID) {
        return manufacturerService.getEntityDto(manufacturerID);
    }
}
