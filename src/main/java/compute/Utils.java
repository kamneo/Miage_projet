package compute;

import DBConnection.DBUtil;

import java.sql.SQLException;
import java.util.ArrayList;
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

    private static List<Node> getAllCombinationsFromString(String prefix, String s, List<Node> res) {
        res.add(new Node(prefix,0,0));
        for (int i = 0; i < s.length(); i++) {
            getAllCombinationsFromString(prefix + s.charAt(i), s.substring(i + 1), res);
        }
        return res;
    }

    public static List<Node> getAllCombinationsFromNbCol(int size){
        return getAllCombinationsFromString("", letters(size),  new ArrayList<Node>());
    }

    public static void setCosts(List<Node> combinations, String tableName) throws SQLException {
        for(Node node : combinations) {
            node.setCost(nbRowsBySubGroup(node.getName(), tableName));
        }
    }

    private static Integer nbRowsBySubGroup(String subGroup, String tableName) throws SQLException {
        return DBUtil.countAllFromSubGroup(subGroup, tableName);
    }

    public static List<String> getProjections(int k, List<Node> nodes){
        List<String> res= new ArrayList<String>();
        int bigestCost = bigestCost(nodes).getCost();

        for (int i=0; i<k; i++){
            Profit highestBenefice = null;
            for (Node node : nodes){
                if(containNodeNamedBy(node.getName(), res))
                    continue;
                int pi = bigestCost - node.getCost();
                int benefice = pi - node.getProfit();
                List<Node> sons = getSons(node, nodes);
                for (Node son : sons) {
                    if(pi-son.getProfit() > 0)
                        benefice+=pi-son.getProfit();
                }
                if (highestBenefice == null || benefice > highestBenefice.getGlobalProfit())
                    highestBenefice = new Profit(node.getName(), pi, benefice);
                                    // nom du noeux, bénéfice individuel, bénéfice total du noeux
            }
            List<Node> sons = getSons(highestBenefice.nodeName, nodes);
            for(Node son : sons)
                for(Node n : nodes)
                    if(n.getName().equals(son.getName()) || n.getName().equals(highestBenefice.nodeName))
                        n.setProfit(highestBenefice.getIndividualProfit());
            res.add(highestBenefice.nodeName);
        }
        return res;
    }

    private static Node bigestCost(List<Node> nodes) {
        Node bigestNode = new Node("", 0,0);
        for (Node n : nodes)
            if(n.getCost() > bigestNode.getCost())
                bigestNode = n;
        return bigestNode;
    }

    private static boolean containNodeNamedBy(String name, List<String> nodes) {
        for(String n : nodes)
            if(n.equals(name))
                return true;
        return false;
    }

    private static List<Node> getSons(Node node, List<Node> nodes) {
        return getSons(node.getName(), nodes);
    }

    private static List<Node> getSons(String nodeName, List<Node> nodes){
        List<Node> sons = getAllCombinationsFromString("", nodeName, new ArrayList<Node>());
        Node toRemove = null;
        for (Node son : sons) {
            for (Node n : nodes)
                if(n.getName().equals(son.getName())) {
                    son.setCost(n.getCost());
                    son.setProfit(n.getProfit());
                }
                if (son.getName().equals(nodeName))
                    toRemove = son;
        }
        sons.remove(toRemove);
        return sons;
    }

    protected static class Profit{
        private String nodeName;
        private int individualProfit;
        private int globalProfit;

        public Profit(String nodeName, int individualProfit, int globalProfit){
            this.nodeName = nodeName;
            this.individualProfit = individualProfit;
            this.globalProfit = globalProfit;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public int getIndividualProfit() {
            return individualProfit;
        }

        public void setIndividualProfit(int individualProfit) {
            this.individualProfit = individualProfit;
        }

        public int getGlobalProfit() {
            return globalProfit;
        }

        public void setGlobalProfit(int globalProfit) {
            this.globalProfit = globalProfit;
        }


    }

}
