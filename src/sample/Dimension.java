package sample;

public class Dimension {
    private String nazTablica;
    private String nazSqlDimTablica;
    private String imeAtrib;
    private String imeSqlAtrib;
    private Integer sifTipAtributa;

    public Dimension() {
        this.nazTablica = "";
        this.nazSqlDimTablica = "";
        this.imeAtrib = "";
        this.imeSqlAtrib = "";
        this.sifTipAtributa = 0;
    }

    public Dimension(String nazTablica) {
        this.nazTablica = nazTablica;
        this.nazSqlDimTablica = "";
        this.imeAtrib = "";
        this.imeSqlAtrib = "";
        this.sifTipAtributa = 0;
    }

    public Dimension(String nazTablica, String nazSqlDimTablica, String imeAtrib, String imeSqlAtrib, Integer sifTipAtributa) {
        this.nazTablica = nazTablica;
        this.nazSqlDimTablica = nazSqlDimTablica;
        this.imeAtrib = imeAtrib;
        this.imeSqlAtrib = imeSqlAtrib;
        this.sifTipAtributa = sifTipAtributa;

    }

    public Integer getSifTipAtributa() {
        return sifTipAtributa;
    }

    public String getNazTablica() {
        return nazTablica;
    }

    public void setNazTablica(String nazTablica) {
        this.nazTablica = nazTablica;
    }

    public String getNazSqlDimTablica() {
        return nazSqlDimTablica;
    }

    public void setNazSqlDimTablica(String nazSqlDimTablica) {
        this.nazSqlDimTablica = nazSqlDimTablica;
    }

    public String getImeAtrib() {
        return imeAtrib;
    }

    public void setImeAtrib(String imeAtrib) {
        this.imeAtrib = imeAtrib;
    }

    public String getImeSqlAtrib() {
        return imeSqlAtrib;
    }

    public void setImeSqlAtrib(String imeSqlAtrib) {
        this.imeSqlAtrib = imeSqlAtrib;
    }

    @Override
    public String toString() {
        if (imeAtrib.equals(""))
            return nazTablica;
        return imeAtrib;
    }
}
