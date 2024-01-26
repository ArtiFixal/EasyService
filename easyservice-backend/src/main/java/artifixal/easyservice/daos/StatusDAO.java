package artifixal.easyservice.daos;

import artifixal.easyservice.entities.Status;
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
public class StatusDAO extends DAOObject{

    public StatusDAO() throws SQLException{
        super();
    }

    public StatusDAO(Connection con) throws SQLException{
        super(con);
    }
    
    public List<Status> getAllStatuses() throws SQLException{
        ArrayList<Status> statuses=new ArrayList<>();
        try(ResultSet results=getAllElements("*","statuses")){
            while(results.next())
                statuses.add(createStatusFromResult(results));
        }
        return statuses;
    }
    
    public Status getStatus(long statusID) throws SQLException{
        try(ResultSet result=getElementByID("*","statuses",statusID)){
            return createStatusFromResult(result);
        }
    }
    
    private Status createStatusFromResult(ResultSet result) throws SQLException{
        return new Status(result.getInt(1),result.getString(2));
    }
}
