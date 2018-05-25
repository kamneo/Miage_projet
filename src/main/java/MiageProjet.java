import DBConnection.DBUtil;
import compute.Node;
import compute.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MiageProjet {
    public static void main(String[] args) throws SQLException {
        boolean onceAgain;
        String tableName;
        String YN;
        do{
            System.out.println("Combien de colonnes contiendra la tables ?");
            int nbCol = new Scanner(System.in).nextInt();
            System.out.println("Combien de lignes contiendra la tables ?");
            int nbRow = new Scanner(System.in).nextInt();
            System.out.println("Combien de valeurs distinctes contiendra la tables ?");
            int nbVal = new Scanner(System.in).nextInt();
            System.out.println("Combien de noeuds voulez vous sauvegarder ?");
            int nbTableSaved = new Scanner(System.in).nextInt();
            System.out.println(nbCol + " " + nbRow + " " + nbVal);
            tableName = DBUtil.generateTable(nbCol, nbRow, nbVal);
            List<Node> combinations = Utils.getAllCombinationsFromNbCol(nbCol);
            Utils.setCosts(combinations, tableName);
            displayNodes(combinations);
            List<String> resultat = Utils.getProjections(nbTableSaved, combinations);
            System.out.println("Pour la table générée les " + nbTableSaved + "Dimentions à stocker sont :");
            System.out.println(resultat);
            System.out.println("Voulez vous recommencer ? (Y/n)");
            YN = new Scanner(System.in).next();
            onceAgain = YN.equals("Y");
        }while(onceAgain);
    }

    private static void displayNodes(List<Node> combinations) {
        System.out.println("Name\t\t|Cost");
        System.out.println("-------------------");
        for (Node n : combinations)
            System.out.println(n.getName() + "\t\t\t| " + n.getCost());
    }
}
