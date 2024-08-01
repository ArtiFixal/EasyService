package artifixal.easyservice.services;

/**
 * Interface providing default functions that have to be tested.
 * 
 * @author ArtiFixal
 */
public interface ServiceUnitTest {
    
    public abstract void canAddEntity();
    
    public abstract void canEditEntity();
    
    public abstract void canConvertEntityToDto();
    
    public abstract void canConvertDtoToEntity();
}
