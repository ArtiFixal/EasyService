package artifixal.easyservice.repositories;

import artifixal.easyservice.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ArtiFixal
 */
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long>{

}
