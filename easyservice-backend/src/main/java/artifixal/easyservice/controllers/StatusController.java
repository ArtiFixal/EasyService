package artifixal.easyservice.controllers;

import artifixal.easyservice.daos.StatusDAO;
import artifixal.easyservice.entities.Status;
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
public class StatusController {

    @Autowired
    public StatusDAO dao;
    
    @GetMapping("/getAllStatuses")
    public List<Status> getAllStatuses() throws SQLException{
        return dao.getAllStatuses();
    }
    
    @GetMapping("/getStatus/{id}")
    public Status getStatus(@PathVariable long id) throws SQLException{
        return dao.getStatus(id);
    }
}
