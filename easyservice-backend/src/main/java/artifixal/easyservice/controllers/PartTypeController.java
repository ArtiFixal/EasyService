package artifixal.easyservice.controllers;

import artifixal.easyservice.dtos.PartTypeDTO;
import artifixal.easyservice.services.PartTypeService;
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
@RequestMapping(path="/v1/types")
@CrossOrigin(origins={"*"})
public class PartTypeController {

    @Autowired
    private PartTypeService partTypeService;
    
    @PostMapping("/addType")
    public void addPartType(@RequestBody PartTypeDTO partType) {
        partTypeService.addEntity(partType);
    }
    
    @PatchMapping("/editType")
    public void editPartType(@RequestBody PartTypeDTO partType){
        partTypeService.editEntity(partType);
    }
    
    @GetMapping("/getAllTypes")
    public Page<PartTypeDTO> getAllPartTypes(Pageable page) {
        return partTypeService.getEntitiesDtoPage(page);
    }
    
    @GetMapping("/getType/{typeID}")
    public PartTypeDTO getPartType(@PathVariable Long typeID) {
        return partTypeService.getEntityDto(typeID);
    }
}
