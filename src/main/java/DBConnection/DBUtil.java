package DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Random;

public class DBUtil {
    public static void generateTable(int nbCol, int nbRow, int nbVal) throws SQLException {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        Calendar calendar = Calendar.getInstance();
        String tableName = calendar.getTimeInMillis() + "_table";
        sb.append(tableName + " (");
        char c = 'A';
        for(int i = 0; i < nbCol;i++) {
            sb.append(c++);
            sb.append(" INTEGER,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");

        executeStatement(sb.toString());

        fillRows(tableName, nbCol, nbRow, nbVal);
        DBConnection dbConnection = DBConnection.getInstance();
        dbConnection.closeDBConnection();
    }

    public static void fillRows(String tableName, int nbCol, int nbRow, int nbVal){
        for(int i = 0; i < nbRow; i++){
            StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
            for (int y = 0; y < nbCol; y++)
                sb.append(randomValueBetween(1, nbVal) + ",");
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            executeStatement(sb.toString());
        }
    }

    private static int randomValueBetween(int min, int max){
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    public static void executeStatement(String sql){
        Statement stmt = null;
        try {
            stmt = DBConnection.getInstance().getConnection().createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
