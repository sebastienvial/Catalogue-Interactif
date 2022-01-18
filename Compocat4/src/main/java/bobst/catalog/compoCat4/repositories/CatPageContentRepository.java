package bobst.catalog.compoCat4.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import bobst.catalog.compoCat4.models.CatPageContent;
import bobst.catalog.compoCat4.models.Part;

public interface CatPageContentRepository extends CrudRepository<CatPageContent, Long> {

	public ArrayList<CatPageContent> findByIdPage(String idPage);

	@Query("select new bobst.catalog.compoCat4.models.Part(c.repere, c.idItem, i.descriptionFr, i.descriptionEn) from CatPageContent c, CatItem i where c.idItem = i.idItem and c.idPage = ?1")
	public ArrayList<Part> findPartsByIdPage(String idPage);

	@Query("select count(*) from CatPageContent c where c.idPage = ?1 and c.contentType = ?2 and c.idItem = ?3 and c.repere = ?4")
	public Integer exists(String idPage, String contentType, String idItem, String repere);

}
