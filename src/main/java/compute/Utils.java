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

    private static List<String> getAllCombinationsFromString(String prefix, String s, List<String> res) {
        res.add(prefix);
        for (int i = 0; i < s.length(); i++) {
            getAllCombinationsFromString(prefix + s.charAt(i), s.substring(i + 1), res);
        }
        return res;
    }

    public static List<String> getAllCombinationsFromNbCol(int size){
        List<String> res = new ArrayList<String>();
        return getAllCombinationsFromString("", letters(size), res);
    }

    public static List<Node> getCosts(List<String> combinations, String tableName) throws SQLException {
        List<Node> costs = new ArrayList<Node>();
        for(String subGroup : combinations) {
            costs.add(new Node(subGroup, nbRowsBySubGroup(subGroup, tableName)));
        }
        return costs;
    }

    private static Integer nbRowsBySubGroup(String subGroup, String tableName) throws SQLException {
        return DBUtil.countAllFromSubGroup(subGroup, tableName);
    }

    private static List<Node> getProjections(int k, String tableName, List<Node> nodes){
        List<Node> res= new ArrayList<Node>();

        //for(k)
        for (int i=0; i<k; i++){
            Node highestBenefice = null;
            //Pour chaque noeud on calcul son benefice
            for (Node node : nodes){
                if(containNodeNamedBy(node.getName(), res))
                    continue;
                int benefice = 0;
                int pi = 0;//computeBenefice(node);
                List<Node> sons = Node.getSons(node,res);
                for (Node son : sons) {
                    if(son.getProfite()>pi)
                        benefice+= pi;
                    else
                        benefice+=pi-son.getProfite();
                }
                if (highestBenefice == null || benefice > highestBenefice.getProfite())
                    highestBenefice = new Node(node.getName(), 0, benefice);
            }
            //res.add();

        }
        return res;
    }

    private static boolean containNodeNamedBy(String name, List<Node> nodes) {
        for(Node n : nodes)
            if(n.getName().equals(name))
                return true;
        return false;
    }

}
