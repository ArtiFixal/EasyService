package artifixal.easyservice.entities;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author ArtiFixal
 */
@Embeddable
@Getter
@EqualsAndHashCode
public class CompatibilityKey {
    private Long partID;
    private Long deviceID;
}
