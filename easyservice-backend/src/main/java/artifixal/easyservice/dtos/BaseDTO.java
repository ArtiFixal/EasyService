package artifixal.easyservice.dtos;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Base class for DTO's.
 * 
 * @author ArtiFixal
 * @param <ID> Type of DTO ID.
 */
@Getter
@AllArgsConstructor
public abstract class BaseDTO<ID> {
    private Optional<ID> id;

    public BaseDTO(){
        id=Optional.empty();
    }
}
