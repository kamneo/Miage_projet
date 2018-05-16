package DBConnection;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDBUtil {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private static Connection connection;
    private String tableName = "TU_table";

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
    public void createGeneratedTable() throws SQLException {
        DBUtil.dropTable(tableName);
        DBUtil.generateTable(tableName, 5, 5, 5);
        Assert.assertEquals(5, DBUtil.countAllFrom(tableName));
    }

    @Test
    public void countAllFromSubGroup() throws SQLException {
        int res = DBUtil.countAllFromSubGroup("ABC", tableName);
        Assert.assertTrue(res <= DBUtil.countAllFrom(tableName));
    }

    @Test(expected = NullPointerException.class)
    public void dropTable() throws SQLException {
        DBUtil.dropTable(tableName);
        DBUtil.countAllFrom(tableName);
    }
}
