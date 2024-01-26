package artifixal.easyservice.controllers;

import artifixal.easyservice.daos.ManufacturerDAO;
import artifixal.easyservice.daos.RequestException;
import artifixal.easyservice.entities.Manufacturer;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ArtiFixal
 */
@CrossOrigin(origins={"*"})
@RestController
public class ManufacturerController {

    @Autowired
    private ManufacturerDAO dao;
    
    @PostMapping("/addManufacturer")
    public void addManufacturer(@RequestParam("name") String name) throws SQLException{
        dao.addManufacturer(name);
    }
    
    @GetMapping("/getAllManufacturers")
    public List<Manufacturer> getAllManufacturers() throws SQLException{
        return dao.getAllManufacturers();
    }
    
    @GetMapping("/getManufacturer/{id}")
    public Manufacturer getManufacturer(@PathVariable long id) throws SQLException, RequestException{
        return dao.getManufacturer(id);
    }
}
