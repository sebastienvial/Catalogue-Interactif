package bobst.catalog.compoCat4.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CatBom {

    @Id   
    @GeneratedValue 
    private Long id;
    private String idDoc;
    private String itemToc;
    private String itemParent;

    public CatBom() {}    

    public CatBom( String idDoc, String itemToc, String itemParent) {
        this.idDoc = idDoc;
        this.itemToc = itemToc;
        this.itemParent = itemParent;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getItemToc() {
        return itemToc;
    }

    public void setItemToc(String itemToc) {
        this.itemToc = itemToc;
    }

    public String getItemParent() {
        return itemParent;
    }

    public void setItemParent(String itemParent) {
        this.itemParent = itemParent;
    }

    
    
}
