package sample;

import java.util.ArrayList;

/**
 * Created by sebo on 11/20/18.
 * janko.sebastian@gmail.com
 */
public class MetadataRetriever {
    public MetadataRetriever() {
        //connect to metadata db
    }

    public ArrayList<String> getFactTables() {
        //select * from tablica where sifTipTablica = 1
        return null;
    }

    public ArrayList<String> getAttributes() {
        /*SELECT *
                FROM tabAtribut, agrFun, tablica, tabAtributAgrFun
        WHERE tabAtribut.sifTablica =  100
                --  Zamijeniti
        AND tabAtribut.sifTablica = tablica.sifTablica
        AND tabAtribut.sifTablica  = tabAtributAgrFun.sifTablica
        AND tabAtribut.rbrAtrib  = tabAtributAgrFun.rbrAtrib
        AND tabAtributAgrFun.sifAgrFun = agrFun.sifAgrFun
        AND tabAtribut.sifTablica = tablica.sifTablica
        AND sifTipAtrib = 1
        ORDER BY tabAtribut.rbrAtrib*/
        return null;
    }

    public ArrayList<String> getDimensions() {
        /*SELECT   dimTablica.nazTablica
        , dimTablica.nazSQLTablica  AS nazSqlDimTablica
        , cinjTablica.nazSQLTablica AS nazSqlCinjTablica
        , cinjTabAtribut.imeSqlAtrib AS cinjTabKljuc
        , dimTabAtribut.imeSqlAtrib  AS dimTabKljuc
        , tabAtribut.*
        FROM tabAtribut, dimCinj
        , tablica dimTablica, tablica cinjTablica
        , tabAtribut cinjTabAtribut, tabAtribut dimTabAtribut
        WHERE dimCinj.sifDimTablica  = dimTablica.sifTablica
        AND dimCinj.sifCinjTablica = cinjTablica.sifTablica
        AND dimCinj.sifCinjTablica = cinjTabAtribut.sifTablica
        AND dimCinj.rbrCinj = cinjTabAtribut.rbrAtrib
        AND dimCinj.sifDimTablica = dimTabAtribut.sifTablica
        AND dimCinj.rbrDim = dimTabAtribut.rbrAtrib
        AND tabAtribut.sifTablica  = dimCinj.sifDimTablica
        AND sifCinjTablica = 100 --  Zamijeniti
        AND tabAtribut.sifTipAtrib = 2
        ORDER BY dimTablica.nazTablica, rbrAtrib*/
        return null;
    }


}
