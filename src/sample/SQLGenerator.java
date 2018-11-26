package sample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SQLGenerator {
    public static String generateSELECTStatement(ArrayList<Dimension> selectedDimensions, ArrayList<Attribute> selectedAttributes) {
        String stmt = "SELECT ";
        for (Dimension d : selectedDimensions) {
            stmt += d.getNazSqlDimTablica() + "." + d.getImeSqlAtrib() + " AS '" + d.getImeAtrib() + "'\n, ";
        }

        for (Attribute a : selectedAttributes) {
            stmt += a.getNazAgrFun() + "(" + a.getNazSQLTablica() + "." + a.getImeSQLAtrib() + ") AS '" + a.getImeAtrib() + "'\n, ";
        }

        stmt = removeLastComma(stmt);

        return stmt;
    }

    public static String generateFROMStatement(ArrayList<Dimension> selectedDimensions, ArrayList<Attribute> selectedAttributes) {
        String stmt = "FROM ";

        Set<String> factTables = new HashSet<>();
        for (Attribute a : selectedAttributes) {
            factTables.add(a.getNazSQLTablica());
        }
        for (String s : factTables) {
            stmt += s + "\n, ";
        }

        Set<String> dimensionTables = new HashSet<>();
        for (Dimension d : selectedDimensions) {
            dimensionTables.add(d.getNazSqlDimTablica());
        }
        for (String s : dimensionTables) {
            stmt += s + "\n, ";
        }

        stmt = removeLastComma(stmt);

        return stmt;
    }

    public static String generateWHEREStatement(MetadataRetriever mdr, FactTable ft, ArrayList<Dimension> selectedDimensions, ArrayList<Attribute> selectedAttributes) {
        String stmt = "WHERE ";
        ArrayList<WhereAttribs> whereAttribs = mdr.getKeys(ft, selectedDimensions, selectedAttributes);

        if (whereAttribs == null) return "";

        for (WhereAttribs wa : whereAttribs) {
            stmt += wa.nazSQLTabl + "." + wa.imeSQLAtrib + "=" + "Dim" + wa.imeSQLAtrib.replace("Key", "") + "." + wa.imeSQLAtrib + " AND ";
        }

        stmt = stmt.substring(0, stmt.length() - 4);
        stmt += "\n";

        return stmt;
    }

    public static String generateGROUPBYStatement(ArrayList<Dimension> selectedDimensions) {
        if (selectedDimensions.size() == 0) return "";

        String stmt = "GROUP BY ";

        for (Dimension d : selectedDimensions) {
            stmt += d.getNazSqlDimTablica() + "." + d.getImeSqlAtrib() + "\n, ";
        }

        stmt = removeLastComma(stmt);
        return stmt;
    }

    private static String removeLastComma(String stmt) {
        return stmt.substring(0, stmt.length() - 2);
    }
}
