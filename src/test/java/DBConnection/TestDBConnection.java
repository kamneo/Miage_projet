package DBConnection;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDBConnection {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private static Connection connection;

    @BeforeClass
    public static void connectionTest(){
        DBConnection dbConnection = DBConnection.getInstance();
        connection = dbConnection.getConnection();
        Assert.assertNotNull(connection);
    }

    @AfterClass
    public static void close() throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance();
        dbConnection.closeDBConnection();
    }

    @Test
    public void t1CreateTable(){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "CREATE TABLE test(id INTEGER NOT NULL)";

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t2DropTable(){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "DROP TABLE test";

        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
