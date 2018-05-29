package core;

import DBConnection.DBUtil;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtils {
    private static final String tableTestName = "testalgotable";
    private static final String pathnameToSqlFile = "src\\main\\resources\\testalgotable.sql";

    @Test
    public void allCombinations(){
        int size = 3;
        List<Node> res = Utils.getAllCombinationsFromNbCol(size);
        assertEquals((int)Math.pow(2, size), res.size());
    }

    @Test
    public void setCosts() throws SQLException {
        DBUtil.dropTable(tableTestName);
        DBUtil.executeSQLFile(pathnameToSqlFile);
        int nbCol = DBUtil.getNbColumn(tableTestName);
        assertEquals(3, nbCol);
        List<Node> combinations = Utils.getAllCombinationsFromNbCol(nbCol);
        Utils.setCosts(combinations, tableTestName);
        List<Node> costsExpected = nodesExpected();
        for(Node n : combinations) {
            for (Node nodeExcepted : costsExpected)
                if (n.getName().equals(nodeExcepted.getName()))
                    assertEquals(nodeExcepted.getCost(), n.getCost());
        }
        List<String> projection = Utils.getProjections(3, combinations);
        assertEquals(3, projection.size());
        assertTrue(projection.contains("AC"));
        assertTrue(projection.contains("B"));
        assertTrue(projection.contains("BC"));
        assertTrue(!projection.contains("ABC"));
        assertTrue(!projection.contains(""));
        assertTrue(!projection.contains("AB"));
        assertTrue(!projection.contains("A"));
        assertTrue(!projection.contains("C"));
    }

    private List<Node> nodesExpected() {
        List<Node> res = new ArrayList<Node>();
        res.add(new Node("", 1, 0));
        res.add(new Node("A", 19, 0));
        res.add(new Node("B", 19, 0));
        res.add(new Node("C", 19, 0));
        res.add(new Node("AB", 340, 0));
        res.add(new Node("AC", 335, 0));
        res.add(new Node("BC", 338, 0));
        res.add(new Node("ABC", 926, 0));

        return res;
    }
}
