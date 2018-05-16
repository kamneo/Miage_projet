package compute;

import DBConnection.DBUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    private static String letters(int size){
        String chars = "";
        char a = 'A';
        for (int i = 0; i<size;i++){
            chars += String.valueOf(a);
            a++;
        }
        return chars;
    }

    private static List<String> compute(String prefix, String s, List<String> res) {
        res.add(prefix);
        for (int i = 0; i < s.length(); i++) {
            compute(prefix + s.charAt(i), s.substring(i + 1), res);
        }
        return res;
    }

    public static List<String> allCombinations(int size){
        List<String> res = new ArrayList<String>();
        return compute("", letters(size), res);
    }

    public static Map<String, Integer> nbRowsBySubGroups(List<String> combinations, String tableName) throws SQLException {
        Map<String, Integer> costs = new HashMap<String, Integer>();
        for(String subGroup : combinations) {
            costs.put(subGroup, nbRowsBySubGroup(subGroup, tableName));
        }
        return costs;
    }

    private static Integer nbRowsBySubGroup(String subGroup, String tableName) throws SQLException {
        return DBUtil.countAllFromSubGroup(subGroup, tableName);
    }


}
