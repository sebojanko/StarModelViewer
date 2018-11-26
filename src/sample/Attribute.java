package sample;

public class Attribute {
    private String imeAtrib;
    private String imeSQLAtrib;
    private String nazTablica;
    private String nazSQLTablica;
    private String nazAgrFun;
    private Integer sifTipAtributa;

    public Attribute() {
        this.imeAtrib = "";
        this.imeSQLAtrib = "";
        this.nazTablica = "";
        this.nazSQLTablica = "";
        this.nazAgrFun = "";
        this.sifTipAtributa = 0;
    }

    public Attribute(String nazTablica) {
        this.imeAtrib = "";
        this.imeSQLAtrib = "";
        this.nazTablica = nazTablica;
        this.nazSQLTablica = "";
        this.nazAgrFun = "";
        this.sifTipAtributa = 0;
    }

    public Attribute(String imeAtrib, String imeSQLAtrib, String nazTablica, String nazSQLTablica, String nazAgrFun, Integer sifTipAtributa) {

        this.imeAtrib = imeAtrib;
        this.imeSQLAtrib = imeSQLAtrib;
        this.nazTablica = nazTablica;
        this.nazSQLTablica = nazSQLTablica;
        this.nazAgrFun = nazAgrFun;
        this.sifTipAtributa = sifTipAtributa;
    }

    public String getNazAgrFun() {
        return nazAgrFun;
    }

    public void setNazAgrFun(String nazAgrFun) {
        this.nazAgrFun = nazAgrFun;
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

    public Integer getSifTipAtributa() {
        return sifTipAtributa;
    }

    @Override
    public String toString() {
        if (imeAtrib.equals(""))
            return nazTablica;
        return imeAtrib;
    }
}
