import DBConnection.DBUtil;

import java.sql.SQLException;
import java.util.Scanner;

public class MiageProjet {
    public static void main(String[] args) throws SQLException {
        boolean onceAgain = false;
        do{
            System.out.println("Combien de colonnes contiendra la tables ?");
            int nbCol = new Scanner(System.in).nextInt();
            System.out.println("Combien de lignes contiendra la tables ?");
            int nbRow = new Scanner(System.in).nextInt();
            System.out.println("Combien de valeurs distinctes contiendra la tables ?");
            int nbVal = new Scanner(System.in).nextInt();
            System.out.println(nbCol + " " + nbRow + " "+ nbVal);
            DBUtil.generateTable(nbCol, nbRow, nbVal);

            System.out.println("Voulez vous recommencer ? (Y/n)");
            String c = new Scanner(System.in).next();
            onceAgain = c.equals("Y");
        }while(onceAgain);
    }
}
