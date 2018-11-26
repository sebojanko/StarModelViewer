package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sebo on 11/20/18.
 * janko.sebastian@gmail.com
 */
public class MetadataRetriever {
    static Connection dbConn;

    MetadataRetriever() {
        dbConn = connectToDB();
    }

    private Connection connectToDB() {
        Connection conn = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("SQL Server JDBC driver je ucitan i registriran.");
        } catch (ClassNotFoundException exception) {
            System.out.println("Pogreska: nije uspjelo ucitavanje JDBC driver-a.");
            System.out.println(exception.getMessage());
            System.exit(-1);
        }

        try {
            conn = DriverManager.getConnection(ConfigReader.getConnectionString());
            System.out.println("Konekcija je uspostavljena.");
        } catch (SQLException exception) {
            System.out.println("Pogreska: nije uspjelo uspostavljanje konekcije.");
            System.out.println(exception.getErrorCode() + " " + exception.getMessage());
            System.exit(-1);
        }

        return conn;
    }

    public ArrayList<FactTable> getFactTables() {
        ArrayList<FactTable> factTables = new ArrayList<>();
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(Queries.GET_FACT_TABLES);
            while (rs.next()) {
                FactTable ft = new FactTable(rs.getInt("sifTablica"), rs.getString("nazTablica").trim(), rs.getString("nazSQLTablica").trim());
                factTables.add(ft);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factTables;
    }

    public ArrayList<Attribute> getAttributes(FactTable ft) {
        ArrayList<Attribute> attributes = new ArrayList<>();
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(Queries.GET_ATTRIBUTES.replace("SIF_TABLICA", ft.getSifTablica().toString()));
            while (rs.next()) {
                Attribute at = new Attribute(rs.getString("imeAtribAgr").trim(), rs.getString("imeSQLAtrib").trim(), rs.getString("nazTablica").trim(), rs.getString("nazSQLTablica").trim(), rs.getString("nazAgrFun").trim(), rs.getInt("sifTipAtrib"));
                attributes.add(at);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attributes;

    }

    public ArrayList<Dimension> getDimensions(FactTable ft) {
        ArrayList<Dimension> dimensions = new ArrayList<>();
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(Queries.GET_DIMENSIONS.replace("SIF_TABLICA", ft.getSifTablica().toString()));
            while (rs.next()) {
                Dimension d = new Dimension(rs.getString("nazTablica").trim(), rs.getString("nazSqlDimTablica").trim(), rs.getString("imeAtrib").trim(), rs.getString("imeSqlAtrib").trim(), rs.getInt("sifTipAtrib"));
                dimensions.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dimensions;
    }

    public ArrayList<WhereAttribs> getKeys(FactTable ft, ArrayList<Dimension> selectedDimensions, ArrayList<Attribute> selectedAttributes) {
        ArrayList<WhereAttribs> whereAttribs = new ArrayList<>();
        String attribsQryPart = "";
        String sifFact = "and ta.sifTablica=" + ft.getSifTablica().toString();

        if (selectedAttributes.size() == 0 && selectedDimensions.size() == 0) return null;
        if (selectedAttributes.size() == 0) return null;
        if (selectedDimensions.size() == 0) return null;
        else {
            attribsQryPart += "AND (";
        }

        Set<String> dimensionsSet = new HashSet<>();
        for (Dimension d : selectedDimensions) {
            dimensionsSet.add(d.getNazSqlDimTablica());
        }

        for (String s : dimensionsSet) {
            attribsQryPart += "ta.imeAtrib like '" + s.replace("Dim", "") + "%' OR ";
        }

        if (attribsQryPart.length() != 0) {
            attribsQryPart = attribsQryPart.substring(0, attribsQryPart.length() - 4);
            attribsQryPart += ")";
        }

        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(Queries.GET_KEYS.replace("SIF_FACT", sifFact).replace("ATRIBS", attribsQryPart));
            while (rs.next()) {
                WhereAttribs w = new WhereAttribs(rs.getString("nazSQLTablica").trim(), rs.getString("imeSQLAtrib").trim());
                whereAttribs.add(w);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return whereAttribs;
    }


}
