package bobst.catalog.compoCat4.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CatE43 {

    @Id
    private String idE43;
    private String idItem;
    private Date dateD;
    private Date dateF;
    private String otp;

    public CatE43() {}

    public CatE43(String idE43, String idItem, Date dateD, Date dateF, String otp) {
        this.idE43 = idE43;
        this.idItem = idItem;
        this.dateD = dateD;
        this.dateF = dateF;
        this.otp = otp;
    }

    public String getIdE43() {
        return idE43;
    }

    public void setIdE43(String idE43) {
        this.idE43 = idE43;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public Date getDateD() {
        return dateD;
    }

    public void setDateD(Date dateD) {
        this.dateD = dateD;
    }

    public Date getDateF() {
        return dateF;
    }

    public void setDateF(Date dateF) {
        this.dateF = dateF;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    

    
    
}
