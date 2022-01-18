package bobst.catalog.compoCat4.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CatItem {
	
	public CatItem() {}
	
	
	
	public CatItem(String idItem, String criticality, String itemRevision, String itemState, String descriptionFr,
			String descriptionEn, String descriptionDe, String descriptionIt, String descriptionPt) {
		super();
		this.idItem = idItem;
		this.criticality = criticality;
		this.itemRevision = itemRevision;
		this.itemState = itemState;
		this.descriptionFr = descriptionFr;
		this.descriptionEn = descriptionEn;
		this.descriptionDe = descriptionDe;
		this.descriptionIt = descriptionIt;
		this.descriptionPt = descriptionPt;
	}



	@Id
	private String idItem;
	private String criticality;
	private String itemRevision;
	private String itemState;
	private String descriptionFr;
	private String descriptionEn;
	private String descriptionDe;
	private String descriptionIt;
	private String descriptionPt;
	
	public String getIdItem() {
		return idItem;
	}
	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}



	public String getCriticality() {
		return criticality;
	}

	public void setCriticality(String criticality) {
		this.criticality = criticality;
	}



	public String getItemRevision() {
		return itemRevision;
	}



	public void setItemRevision(String itemRevision) {
		this.itemRevision = itemRevision;
	}



	public String getItemState() {
		return itemState;
	}



	public void setItemState(String itemState) {
		this.itemState = itemState;
	}



	public String getDescriptionFr() {
		return descriptionFr;
	}



	public void setDescriptionFr(String descriptionFr) {
		this.descriptionFr = descriptionFr;
	}



	public String getDescriptionEn() {
		return descriptionEn;
	}



	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}



	public String getDescriptionDe() {
		return descriptionDe;
	}



	public void setDescriptionDe(String descriptionDe) {
		this.descriptionDe = descriptionDe;
	}



	public String getDescriptionIt() {
		return descriptionIt;
	}



	public void setDescriptionIt(String descriptionIt) {
		this.descriptionIt = descriptionIt;
	}



	public String getDescriptionPt() {
		return descriptionPt;
	}



	public void setDescriptionPt(String descriptionPt) {
		this.descriptionPt = descriptionPt;
	}
	
	

}
