package bobst.catalog.compoCat4.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CatDoc {

    @Id
    private String idDoc;
    private Date dateEclatement;
    private String otp;

    public CatDoc() {}

    public CatDoc(String idDoc, Date dateEclatement, String otp) {
        this.idDoc = idDoc;
        this.dateEclatement = dateEclatement;
        this.otp = otp;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public Date getDateEclatement() {
        return dateEclatement;
    }

    public void setDateEclatement(Date dateEclatement) {
        this.dateEclatement = dateEclatement;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    

    
}
