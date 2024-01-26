package artifixal.easyservice.daos;

import artifixal.easyservice.entities.Manufacturer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ArtiFixal
 */
@Repository
public class ManufacturerDAO extends DAOObject{

    public ManufacturerDAO() throws SQLException{
        super();
    }
    
    public ManufacturerDAO(Connection con) throws SQLException{
        super(con);
    }
    
    public long addManufacturer(String name) throws SQLException{
        try(PreparedStatement insert=createInsertStatement("manufacturers",true,name)){
            if(insert.executeUpdate()==1)
                return getLastInsertedId();
            return -1;
        }
    }
    
    public List<Manufacturer> getAllManufacturers() throws SQLException{
        ArrayList<Manufacturer> manufacturers=new ArrayList<>();
        try(ResultSet result=getAllElements("*","manufacturers")){
            while(result.next())
            {
                manufacturers.add(createManufacturerFromResult(result));
            }
        }
        return manufacturers;
    }
    
    public Manufacturer getManufacturer(long manufacturerID) throws SQLException, RequestException{
        try(ResultSet result=getElementByID("*","manufacturers",manufacturerID)){
            if(!result.next())
                throw new RequestException("There is nomManufacturer with given ID:"+manufacturerID);
            return createManufacturerFromResult(result);
        }
    }
    
    private Manufacturer createManufacturerFromResult(ResultSet result) throws SQLException{
        return new Manufacturer(result.getLong(1),result.getString(2));
    }
}
