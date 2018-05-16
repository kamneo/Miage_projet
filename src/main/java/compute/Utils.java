package compute;

import DBConnection.DBUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Node> nbRowsBySubGroups(List<String> combinations, String tableName) throws SQLException {
        List<Node> costs = new ArrayList<Node>();
        for(String subGroup : combinations) {
            costs.add(new Node(subGroup, nbRowsBySubGroup(subGroup, tableName)));
        }
        return costs;
    }

    private static Integer nbRowsBySubGroup(String subGroup, String tableName) throws SQLException {
        return DBUtil.countAllFromSubGroup(subGroup, tableName);
    }

    private static List<String> getProjections(int k, String table, List<Node> nodes){
        List<Node> res= new ArrayList<Node>();

        //for(k)
        for (int i=0; i<k; i++){
            Node highestB = null;
            //Pour chaque noeud on calcul son benefice
            for (Node node : nodes){
                if(node.getName().isContaintedIn(res))
                    continue;
                int b = 0;
                int pi = benefice(node);
                List<Node> sons = DBUtil.getSons(node,table,res);
                for (Node son : sons) {
                    if(son.getProfite()>pi)
                        b+= pi;
                    else
                        b+=pi-son.getProfite();
                }
                if (highestB==null || b > highestB.getProfite())
                    highestB = new Node(node.getName(), 0, b);
            }
            res.add();

        }
        return res;
    }

}
