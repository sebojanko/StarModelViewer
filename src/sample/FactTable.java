package sample;

public class FactTable {
    private Integer sifTablica;
    private String nazTablica;
    private String nazSQLTablica;

    FactTable(Integer sifTablica, String nazTablica, String nazSQLTablica) {

        this.sifTablica = sifTablica;
        this.nazTablica = nazTablica;
        this.nazSQLTablica = nazSQLTablica;
    }

    public Integer getSifTablica() {
        return sifTablica;
    }

    public void setSifTablica(Integer sifTablica) {
        this.sifTablica = sifTablica;
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

    @Override
    public String toString() {
        return nazTablica;
    }
}
