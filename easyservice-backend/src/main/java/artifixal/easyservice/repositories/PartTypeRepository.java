package artifixal.easyservice.repositories;

import artifixal.easyservice.entities.PartType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ArtiFixal
 */
@Repository
public interface PartTypeRepository extends JpaRepository<PartType,Long>{

}
