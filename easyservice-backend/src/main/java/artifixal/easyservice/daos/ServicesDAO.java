package artifixal.easyservice.daos;

import artifixal.easyservice.entities.Service;
import java.sql.Connection;
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
public class ServicesDAO extends DAOObject{

    public ServicesDAO() throws SQLException{
        super();
    }

    public ServicesDAO(Connection con) throws SQLException{
        super(con);
    }
    
    public List<Service> getAllServices() throws SQLException{
        ArrayList<Service> services=new ArrayList<>();
        try(ResultSet result=getAllElements("*","services")){
            while(result.next())
            {
                services.add(createServiceFromResult(result));
            }
            return services;
        }
    }
    
    public Service getService(long sericeID) throws SQLException, RequestException{
        try(ResultSet result=getElementByID("*","services",sericeID)){
            if(!result.next())
                throw new RequestException("There is no service with given ID:"+sericeID);
            return createServiceFromResult(result);
        }
    }
    
    private Service createServiceFromResult(ResultSet result) throws SQLException{
        return new Service(result.getLong(1),result.getString(2),result.getBigDecimal(3));
    }
}
