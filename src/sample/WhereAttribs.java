package sample;

class WhereAttribs {
    String dimTablica;
    String cinjKey;
    String cinjSQLKey;
    String dimKey;
    String dimSQLKey;

    public WhereAttribs(String dimTablica, String cinjKey, String cinjSQLKey, String dimKey, String dimSQLKey) {
        this.dimTablica = dimTablica;
        this.cinjKey = cinjKey;
        this.cinjSQLKey = cinjSQLKey;
        this.dimKey = dimKey;
        this.dimSQLKey = dimSQLKey;
    }

    public String getDimTablica() {
        return dimTablica;
    }

    public String getCinjKey() {
        return cinjKey;
    }

    public String getCinjSQLKey() {
        return cinjSQLKey;
    }

    public String getDimKey() {
        return dimKey;
    }

    public String getDimSQLKey() {
        return dimSQLKey;
    }
}
