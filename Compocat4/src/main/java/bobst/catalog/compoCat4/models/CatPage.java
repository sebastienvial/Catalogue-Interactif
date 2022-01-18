package bobst.catalog.compoCat4.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CatPage {
    
    @Id
    private String idPage;
    private String idE43;

    public CatPage() {}

    public CatPage(String idPage, String idE43) {
        this.idPage = idPage;
        this.idE43 = idE43;
    }

    public String getIdPage() {
        return idPage;
    }

    public void setIdPage(String idPage) {
        this.idPage = idPage;
    }

    public String getIdE43() {
        return idE43;
    }

    public void setIdE43(String idE43) {
        this.idE43 = idE43;
    }

    
    
}
