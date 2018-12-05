package sample;

import java.sql.*;
import java.util.ArrayList;

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
                Dimension d = new Dimension(rs.getString("nazTablica").trim(), rs.getString("nazSqlDimTablica").trim(), rs.getString("imeAtrib").trim(), rs.getString("imeSqlAtrib").trim(), rs.getInt("sifTipAtrib"), rs.getInt("sifTablica"));
                dimensions.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dimensions;
    }

    public ArrayList<WhereAttribs> getKeysNEW(FactTable ft, ArrayList<Dimension> selectedDimensions, ArrayList<Attribute> selectedAttributes) {
        ArrayList<WhereAttribs> whereAttribs = new ArrayList<>();
        String dimKeys = "";

        if (selectedAttributes.size() == 0 && selectedDimensions.size() == 0) return null;
        if (selectedAttributes.size() == 0) return null;
        if (selectedDimensions.size() == 0) return null;

        for (Dimension d : selectedDimensions) {
            dimKeys += d.getSifTablica() + ", ";
        }
        dimKeys = dimKeys.substring(0, dimKeys.length() - 2);

        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(Queries.GET_KEYS_NEW.replace("SIF_FACT", ft.getSifTablica().toString()).replace("SIF_DIM", dimKeys));
            while (rs.next()) {
                WhereAttribs w = new WhereAttribs(rs.getString("dimTablica").trim(), rs.getString("cinjKey").trim(), rs.getString("cinjSQLKey").trim(), rs.getString("dimKey").trim(), rs.getString("dimSQLKey").trim());
                whereAttribs.add(w);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return whereAttribs;
    }
}
