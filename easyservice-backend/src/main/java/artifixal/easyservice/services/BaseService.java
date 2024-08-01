package artifixal.easyservice.services;

import artifixal.easyservice.dtos.BaseDTO;
import artifixal.easyservice.entities.BaseEntity;
import artifixal.easyservice.exceptions.EntityNotFoundException;
import artifixal.easyservice.exceptions.InvalidDTOException;
import jakarta.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

/**
 * Class used as base for Services.
 * 
 * @author ArtiFixal
 * 
 * @param <R> Type of repository used to retreive entities.
 * @param <ET> Entity type recived from DB.
 * @param <ID> Type of entity ID.
 * @param <DTO> DTO used int transport type.
 */
@Validated
public abstract class BaseService<R extends JpaRepository<ET,ID>,ET extends BaseEntity,ID,DTO extends BaseDTO<ID>> {
    
    /**
     * Repository used to retrieve entities.
     */
    @Autowired
    protected R repo;
    
    protected final String entityName;
    
    @Autowired(required=true)
    protected BaseService(){
        // Get type name of ET generic.
        entityName=((ParameterizedType)getClass().getGenericSuperclass())
                .getActualTypeArguments()[1].getTypeName();
    }
    
    protected ET getEntity(ID id) throws EntityNotFoundException{
        return repo.findById(id).orElseThrow(()->
                new EntityNotFoundException(entityName,id));
    }
    
    public DTO getEntityDto(ID id) throws EntityNotFoundException{
        return convertEntityToDto(getEntity(id));
    }
    
    public Page<DTO> getEntitiesDtoPage(Pageable page){
        return repo.findAll(page).map((entity)->convertEntityToDto(entity));
    }
    
    public List<DTO> getAllEntitiesDto(){
        return repo.findAll().stream().map((entity)->convertEntityToDto(entity))
                .collect(Collectors.toList());
    }
    
    public ET addEntity(@Valid DTO entityData){
        ET enity=convertDtoToEntity(entityData);
        return repo.save(enity);
    }
    
    /**
     * @param entityNewData Data used to update entity.
     * 
     * @return Saved entity.
     * 
     * @throws EntityNotFoundException If entity with given ID wasn't found.
     * @throws InvalidDTOException If transported data doesn't contain ID.
     */
    public ET editEntity(@Valid DTO entityNewData) throws EntityNotFoundException,InvalidDTOException{
        final ID id=entityNewData.getId().orElseThrow(()->
                new InvalidDTOException("Entity edit request has no ID"));
        ET toEdit=getEntity(id);
        updateEntityValues(entityNewData,toEdit);
        return repo.save(toEdit);
    }
    
    /**
     * Updates entity data with those received from DTO.
     * 
     * @param entityNewData New values.
     * @param entity Where to change.
     */
    protected abstract void updateEntityValues(DTO entityNewData,ET entity);
    
    protected abstract DTO convertEntityToDto(ET entity);
    
    protected abstract ET convertDtoToEntity(DTO entityData);
}
