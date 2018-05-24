package compute;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private String name;
    private int cost;
    private int profite;

    public Node(String name, int cost, int profite) {
        this.name = name;
        this.cost = cost;
        this.profite = profite;
    }

    public Node(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.profite = 0;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getProfite() {
        return profite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setProfite(int profite) {
        this.profite = profite;
    }

    public static List<Node> getSons(Node node, List<Node> nodes) {
        List<Node> res = new ArrayList<Node>();
        for(Node n : nodes){
            if(n.getName().contains(node.getName()))
                res.add(n);
        }
        return res;
    }
}
