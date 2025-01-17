package sample;

public class Queries {
    public static final String GET_ATTRIBUTES = "SELECT tabAtribut.sifTablica, tabAtribut.rbrAtrib, imeSQLAtrib, sifTipAtrib, tabAtribut.imeAtrib,\n" +
            "\t\tagrFun.sifAgrFun, nazAgrFun,\n" +
            "\t\ttablica.sifTablica, nazTablica, nazSQLTablica,\n" +
            "\t\tsifTipTablica, tabAtributAgrFun.sifTablica,\n" +
            "\t\ttabAtributAgrFun.rbrAtrib, tabAtributAgrFun.sifAgrFun, tabAtributAgrFun.imeAtrib as imeAtribAgr\n" +
            "  FROM tabAtribut, agrFun, tablica, tabAtributAgrFun                                          \n" +
            " WHERE tabAtribut.sifTablica = SIF_TABLICA" +
            "   AND tabAtribut.sifTablica = tablica.sifTablica \n" +
            "   AND tabAtribut.sifTablica  = tabAtributAgrFun.sifTablica \n" +
            "   AND tabAtribut.rbrAtrib  = tabAtributAgrFun.rbrAtrib \n" +
            "   AND tabAtributAgrFun.sifAgrFun = agrFun.sifAgrFun \n" +
            "   AND tabAtribut.sifTipAtrib = 1\n" +
            " ORDER BY tabAtribut.rbrAtrib";

    public static final String GET_FACT_TABLES = "SELECT * \n" +
            "  FROM tablica \n" +
            " WHERE sifTipTablica = 1";

    public static final String GET_DIMENSIONS = "SELECT   dimTablica.nazTablica\n" +
            "       , dimTablica.nazSQLTablica  AS nazSqlDimTablica\n" +
            "       , cinjTablica.nazSQLTablica AS nazSqlCinjTablica\n" +
            "       , cinjTabAtribut.imeSQLAtrib AS cinjTabSQLAtrib\n" +
            "       , dimTabAtribut.imeSqlAtrib AS dimTabSQLAtrib\n" +
            "       , tabAtribut.*\n" +
            "  FROM tabAtribut, dimCinj\n" +
            "     , tablica dimTablica, tablica cinjTablica \n" +
            "     , tabAtribut cinjTabAtribut, tabAtribut dimTabAtribut\n" +
            " WHERE dimCinj.sifDimTablica  = dimTablica.sifTablica\n" +
            "   AND dimCinj.sifCinjTablica = cinjTablica.sifTablica\n" +
            "   \n" +
            "   AND dimCinj.sifCinjTablica = cinjTabAtribut.sifTablica\n" +
            "   AND dimCinj.rbrCinj = cinjTabAtribut.rbrAtrib\n" +
            "   \n" +
            "   AND dimCinj.sifDimTablica = dimTabAtribut.sifTablica\n" +
            "   AND dimCinj.rbrDim = dimTabAtribut.rbrAtrib\n" +
            "\n" +
            "   AND tabAtribut.sifTablica  = dimCinj.sifDimTablica\n" +
            "   AND sifCinjTablica = SIF_TABLICA\n" +
            "   AND tabAtribut.sifTipAtrib = 2\n" +
            " ORDER BY dimTablica.nazTablica, rbrAtrib";


    public static final String GET_KEYS_NEW = "select t.nazSQLTablica as cinjTablica, t2.nazSQLTablica as dimTablica, ta.imeAtrib as cinjKey, ta.imeSQLAtrib as cinjSQLKey, ta2.imeAtrib as dimKey, ta2.imeSQLAtrib as dimSQLKey\n" +
            "from dimcinj dc join tabAtribut ta on dc.sifCinjTablica=ta.sifTablica\n" +
            "\t\t\t\tjoin tabAtribut ta2 on dc.sifDimTablica=ta2.sifTablica\n" +
            "\t\t\t\tjoin tablica t on ta.sifTablica=t.sifTablica\n" +
            "\t\t\t\tjoin tablica t2 on ta2.sifTablica=t2.sifTablica\n" +
            "\twhere dc.rbrCinj=ta.rbrAtrib and dc.rbrDim=ta2.rbrAtrib" +
            "\tand ta.sifTablica=SIF_FACT and ta2.sifTablica in (SIF_DIM);";
}
