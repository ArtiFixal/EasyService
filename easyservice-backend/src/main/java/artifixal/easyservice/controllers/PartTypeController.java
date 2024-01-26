package artifixal.easyservice.controllers;

import artifixal.easyservice.daos.PartTypeDAO;
import artifixal.easyservice.entities.PartType;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ArtiFixal
 */
@CrossOrigin(origins={"*"})
@RestController
public class PartTypeController {

    @Autowired
    private PartTypeDAO dao;
    
    @GetMapping("/getAllTypes")
    public List<PartType> getAllPartTypes() throws SQLException{
        return dao.getAllPartTypes();
    }
    
    @GetMapping("/getType/{id}")
    public PartType getPartType(@PathVariable long id) throws SQLException{
        return dao.getPartType(id);
    }
    
    public void addPartType(String name) throws SQLException{
        dao.addPartType(name);
    }
}
