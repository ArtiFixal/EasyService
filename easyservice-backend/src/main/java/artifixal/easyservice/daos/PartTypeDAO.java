package artifixal.easyservice.daos;

import artifixal.easyservice.entities.PartType;
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
public class PartTypeDAO extends DAOObject{

    public PartTypeDAO() throws SQLException{
        super();
    }

    public PartTypeDAO(Connection con) throws SQLException{
        super(con);
    }
    
    public long addPartType(String name) throws SQLException{
        try(PreparedStatement insert=createInsertStatement("types",true,name)){
            if(insert.execute())
                return getLastInsertedId();
            return -1;
        }
    }
    
    public List<PartType> getAllPartTypes() throws SQLException{
        ArrayList<PartType> partTypes=new ArrayList<>();
        try(ResultSet results=getAllElements("*","types")){
            while(results.next())
                partTypes.add(createObjectFromResult(results,PartType.class));
        }
        return partTypes;
    }
    
    public PartType getPartType(long partTypeID) throws SQLException{
        try(ResultSet result=getElementByID("*","types",partTypeID)){
            return createObjectFromResult(result,PartType.class);
        }
    }
}
