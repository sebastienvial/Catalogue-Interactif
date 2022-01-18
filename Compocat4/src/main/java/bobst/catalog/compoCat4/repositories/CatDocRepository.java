package bobst.catalog.compoCat4.repositories;

import org.springframework.data.repository.CrudRepository;

import bobst.catalog.compoCat4.models.CatDoc;

public interface CatDocRepository extends CrudRepository<CatDoc, String> {
    
}
