package DBConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

import java.util.Calendar;
import java.util.Random;

public class DBUtil {
    public static String generateTable(int nbCol, int nbRow, int nbVal) throws SQLException {
        return generateTable(null, nbCol, nbRow, nbVal);
    }

    public static String generateTable(String tableName, int nbCol, int nbRow, int nbVal) throws SQLException {
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
        return tableName;
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

    public static ResultSet executeQuery(String sql){
        Statement stmt = null;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public static int countAllFromSubGroup(String columns, String tableName) throws SQLException {
        if(columns.equals("")) return 1;

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < columns.length(); i++)
            sb.append(columns.charAt(i) + ", ");
        sb.delete(sb.lastIndexOf(", " ), sb.lastIndexOf(", " ) + 1);
        String sql = "SELECT count(*) FROM (SELECT DISTINCT " + sb.toString() +" FROM " + tableName + ") as R";
        ResultSet res = executeQuery(sql);
        res.next();
        return res.getInt(1);
    }

    public static void dropTable(String tableName) {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        executeStatement(sql);
    }

    public static boolean tableExist(String tableName) {
        String sql = "SELECT * FROM "+ tableName + "LIMIT 1";
        Statement stmt = null;
        try {
            stmt = DBConnection.getInstance().getConnection().createStatement();
            stmt.executeQuery(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void executeSQLFile(String pathname)
    {
        String s;
        StringBuffer sb = new StringBuffer();

        try
        {
            FileReader fr = new FileReader(new File(pathname));
            BufferedReader br = new BufferedReader(fr);

            while((s = br.readLine()) != null)
            {
                sb.append(s);
            }
            br.close();
            String[] inst = sb.toString().split(";");
            setAutocommit(false);
            for(int i = 0; i<inst.length; i++)
            {
                if(!inst[i].trim().equals(""))
                    executeStatement(inst[i]);
            }
            setAutocommit(true);
        }
        catch(Exception e)
        {
            System.out.println("*** Error : "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
            System.out.println("################################################");
            System.out.println(sb.toString());
        }
    }

    public static int getNbColumn(String tableName) throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM " + tableName + " LIMIT 1");
        if (rs == null){
            throw new SQLException("Error ! Unknown table named " + tableName);
        }
        return rs.getMetaData().getColumnCount();
    }
}
