package DBConnection;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.SQLException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDBUtil {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private static Connection connection;
    private static final String tableName = "TU_table";

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
    public void t1CreateGeneratedTable() throws SQLException {
        DBUtil.generateTable(tableName, 5, 5, 5);
        Assert.assertEquals(5, DBUtil.countAllFrom(tableName));
    }

    @Test
    public void t2CountAllFromSubGroup() throws SQLException {
        int res = DBUtil.countAllFromSubGroup("ABC", tableName);
        Assert.assertTrue(res <= DBUtil.countAllFrom(tableName));
    }

    @Test(expected = NullPointerException.class)
    public void t3DropTable() throws SQLException {
        DBUtil.dropTable(tableName);
        DBUtil.countAllFrom(tableName);
    }
}
