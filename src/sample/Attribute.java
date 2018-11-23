package sample;

public class Attribute {
    private String imeAtrib;
    private String imeSQLAtrib;
    private String nazTablica;
    private String nazSQLTablica;
    // TODO dodavat stvari kako naide potreba a naici ce


    public Attribute() {
        this.imeAtrib = "";
        this.imeSQLAtrib = "";
        this.nazTablica = "";
        this.nazSQLTablica = "";
    }

    public Attribute(String nazTablica) {
        this.imeAtrib = "";
        this.imeSQLAtrib = "";
        this.nazTablica = nazTablica;
        this.nazSQLTablica = "";
    }

    public Attribute(String imeAtrib, String imeSQLAtrib, String nazTablica, String nazSQLTablica) {

        this.imeAtrib = imeAtrib;
        this.imeSQLAtrib = imeSQLAtrib;
        this.nazTablica = nazTablica;
        this.nazSQLTablica = nazSQLTablica;
    }

    public String getNazTablica() {
        return nazTablica;
    }

    public void setNazTablica(String nazTablica) {
        this.nazTablica = nazTablica;
    }

    public String getNazSQLTablica() {
        return nazSQLTablica;
    }

    public void setNazSQLTablica(String nazSQLTablica) {
        this.nazSQLTablica = nazSQLTablica;
    }

    public String getImeAtrib() {
        return imeAtrib;
    }

    public void setImeAtrib(String imeAtrib) {
        this.imeAtrib = imeAtrib;
    }

    public String getImeSQLAtrib() {
        return imeSQLAtrib;
    }

    public void setImeSQLAtrib(String imeSQLAtrib) {
        this.imeSQLAtrib = imeSQLAtrib;
    }

    @Override
    public String toString() {
        if (imeAtrib.equals(""))
            return nazTablica;
        return imeAtrib;
    }

}
