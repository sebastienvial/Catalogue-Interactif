package models;

public class Cat_Structure {
	
	private String idDoc;
	private String idItem;
	private String idPage;
	private String idParent;
	
	//Contructors ------------------------------------------------
	
	public Cat_Structure () {
		
	}
	
	public Cat_Structure(String idDoc, String idItem, String idPage, String idParent) {
		super();
		this.idDoc = idDoc;
		this.idItem = idItem;
		this.idPage = idPage;
		this.idParent = idParent;
	}

	//-------------------------------------------------------------
	
	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public String getIdItem() {
		return idItem;
	}

	public void setIdItem(String idItem) {
		this.idItem = idItem;
	}

	public String getIdPage() {
		return idPage;
	}

	public void setIdPage(String idPage) {
		this.idPage = idPage;
	}

	public String getIdParent() {
		return idParent;
	}

	public void setIdParent(String idParent) {
		this.idParent = idParent;
	}
	
	
}
