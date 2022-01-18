package bobst.catalog.compoCat4.repositories;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import bobst.catalog.compoCat4.models.CatBom;
import bobst.catalog.compoCat4.models.NodeBom;

public interface CatBomRepository extends CrudRepository<CatBom, Long>{
    
    @Query("select new bobst.catalog.compoCat4.models.NodeBom(i.descriptionFr, b.itemToc, b.itemParent, '') from CatBom b, CatItem i where b.itemToc = i.idItem and b.idDoc = ?1")
	public ArrayList<NodeBom> findBomByIdDoc(String idDoc);

    @Query("select i.descriptionFr from CatItem i, CatBom b where b.itemToc = i.idItem and b.idDoc = ?1 and b.itemParent = ?2")
    public String[] findBomDynamic(String idDoc, String idParent);

    //@Query("select new bobst.catalog.compoCat4.models.Part(c.repere, c.idItem, i.descriptionFr, i.descriptionEn) from CatPageContent c, CatItem i where c.idItem = i.idItem and c.idPage = ?1")
	
    
}
