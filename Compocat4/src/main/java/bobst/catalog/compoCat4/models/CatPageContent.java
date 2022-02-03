package bobst.catalog.compoCat4.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;


@Entity
@IdClass(CatPageContentCompositeKey.class)
public class CatPageContent {
	
	public CatPageContent() {
	}
	
	
	public CatPageContent(String idPage, String contentType, String idItem, String repere) {
		super();
		this.idPage = idPage;
		this.contentType = contentType;
		this.idItem = idItem;
		this.repere = repere;
	}

	@Id
	private String idPage;
	@Id
	private String contentType;
	@Id
	private String idItem;
	@Id
	private String repere;
	
	public String getIdPage() {
		return idPage;
	}
	public void setIdPage(String idPage) {
		this.idPage = idPage;
	}
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getIdItem() {
		return idItem;
	}
	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}
	
	public String getRepere() {
		return repere;
	}
	public void setRepere(String repere) {
		this.repere = repere;
	}
	

}
