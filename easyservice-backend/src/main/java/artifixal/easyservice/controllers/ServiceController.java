package artifixal.easyservice.controllers;

import artifixal.easyservice.daos.ServicesDAO;
import artifixal.easyservice.entities.Service;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ArtiFixal
 */
@CrossOrigin(origins={"*"})
@RestController
public class ServiceController {
    
    @Autowired
    private ServicesDAO dao;
    
    @GetMapping("/getAllServices")
    public List<Service> getAllServices() throws SQLException{
        return dao.getAllServices();
    }
    
    @GetMapping("/")
    public String test(){
        return "dasdef";
    }
}
