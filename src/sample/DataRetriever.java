package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataRetriever {
    private static Connection dbConn;

    public static ArrayList<ArrayList<String>> execQuery(String query, ArrayList<Dimension> selectedDimensions, ArrayList<Attribute> selectedAttributes) {
        dbConn = MetadataRetriever.dbConn;
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        try {
            Statement stmt = dbConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ArrayList<String> temp = new ArrayList<>();
                for (Dimension d : selectedDimensions) {
                    temp.add(rs.getString(d.getImeAtrib()));
                }
                for (Attribute a : selectedAttributes) {
                    temp.add(rs.getString(a.getImeAtrib()));
                }
                rows.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }


}
