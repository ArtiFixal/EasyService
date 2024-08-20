package artifixal.easyservice.repositories;

import artifixal.easyservice.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ArtiFixal
 */
@Repository
public interface StatusRepository extends JpaRepository<Status,Long>{

}
