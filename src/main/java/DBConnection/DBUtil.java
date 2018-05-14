package DBConnection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Random;

public class DBUtil {
    public static void generateTable(int nbCol, int nbRow, int nbVal) throws SQLException {
        generateTable(null, nbCol, nbRow, nbVal);
    }

    public static void generateTable(String tableName, int nbCol, int nbRow, int nbVal) throws SQLException {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        Calendar calendar = Calendar.getInstance();
        if(tableName == null)
            tableName = calendar.getTimeInMillis() + "_table";
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
        setAutocommit(false);
        for(int i = 0; i < nbRow; i++){
            StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
            for (int y = 0; y < nbCol; y++)
                sb.append(randomValueBetween(1, nbVal) + ",");
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            executeStatement(sb.toString());
            if(i%1000 == 0)
                commit();
        }
        commit();
        setAutocommit(true);
    }

    private static void commit() {
        try {
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setAutocommit(boolean b) {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(b);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int randomValueBetween(int min, int max){
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    public static void executeStatement(String sql){
        Statement stmt = null;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int countAllFrom(String tableName) throws SQLException {
        String sql = "SELECT count(*) FROM " + tableName;
        ResultSet res = executeQuery(sql);
        res.next();
        return res.getInt(1);
    }

    private static ResultSet executeQuery(String sql) {
        Statement stmt = null;
        try {
            stmt = DBConnection.getInstance().getConnection().createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void dropTable(String tableName) {
        String sql = "DROP TABLE " + tableName;
        executeStatement(sql);
    }
}
