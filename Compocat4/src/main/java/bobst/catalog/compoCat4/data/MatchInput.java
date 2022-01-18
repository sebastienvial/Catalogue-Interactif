package bobst.catalog.compoCat4.data;

public class MatchInput {

    private String level;
    private String node;
    private String numBobst;
    private String valD;
    private String valF;
    private String description;
    private String gme; //Groupe marchandise externe
    private String catNcl; //catégorie nomenclature
    
    public MatchInput() {
    }

    public MatchInput(String level, String node, String numBobst, String valD, String valF, String description,
            String gme, String catNcl) {
        this.level = level;
        this.node = node;
        this.numBobst = numBobst;
        this.valD = valD;
        this.valF = valF;
        this.description = description;
        this.gme = gme;
        this.catNcl = catNcl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getNumBobst() {
        return numBobst;
    }

    public void setNumBobst(String numBobst) {
        this.numBobst = numBobst;
    }

    public String getValD() {
        return valD;
    }

    public void setValD(String valD) {
        this.valD = valD;
    }

    public String getValF() {
        return valF;
    }

    public void setValF(String valF) {
        this.valF = valF;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGme() {
        return gme;
    }

    public void setGme(String gme) {
        this.gme = gme;
    }

    public String getCatNcl() {
        return catNcl;
    }

    public void setCatNcl(String catNcl) {
        this.catNcl = catNcl;
    }

    
     
    
}
