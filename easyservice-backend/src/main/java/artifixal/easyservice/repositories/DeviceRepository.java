package artifixal.easyservice.repositories;

import artifixal.easyservice.entities.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ArtiFixal
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device,Long>{

    /**
     * Finds all devices compatible with the given part.
     * 
     * @param partID Part to search for compatibility.
     * @param page Page of search to return.
     * 
     * @return Paginated result.
     */
    Page<Device> findByCompatibleParts_Key_PartID(Long partID,Pageable page);
}
