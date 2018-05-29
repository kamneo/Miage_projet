package core;

public class Node implements Comparable {

    private String name;
    private int cost;
    private int profit;

    public Node(String name, int cost, int profit) {
        this.name = name;
        this.cost = cost;
        this.profit = profit;
    }

    public Node(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.profit = 0;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getProfit() {
        return profit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int compareTo(Object o) {
        return this.getName().compareTo(((Node)o).getName());
    }
}
