package bobst.catalog.compoCat4.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
    @GeneratedValue
    private Long id; 
	private String idPage;
	private String contentType;
	private String idItem;
	private String repere;
	
//	@OneToOne()
//	@JoinColumn(name="idItem")
//	private CatItem catItem;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


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
