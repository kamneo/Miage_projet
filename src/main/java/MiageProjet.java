import DBConnection.DBUtil;

import java.sql.SQLException;
import java.util.Scanner;

public class MiageProjet {
    public static void main(String[] args) throws SQLException {
        boolean onceAgain;
        String tableName;
        String YN;
        do{
//            System.out.println("Voulez vous utiliser une table existante ? (Y/n)");
//            YN = new Scanner(System.in).next();
//            if(YN.equals("Y")) {
//                boolean tableExist;
//                do {
//                    System.out.println("entrez le nom de la table ?");
//                    tableName = new Scanner(System.in).next();
//                    tableExist = DBUtil.tableExist(tableName);
//                    if(!tableExist){
//                        System.out.println("La table n'hexiste pas.\nEssayer de nouveau? (Y/n)");
//                        YN = new Scanner(System.in).next();
//                    }
//                } while (!tableExist && YN.equals("Y"));
//            }else {
            System.out.println("Combien de colonnes contiendra la tables ?");
            int nbCol = new Scanner(System.in).nextInt();
            System.out.println("Combien de lignes contiendra la tables ?");
            int nbRow = new Scanner(System.in).nextInt();
            System.out.println("Combien de valeurs distinctes contiendra la tables ?");
            int nbVal = new Scanner(System.in).nextInt();
            System.out.println(nbCol + " " + nbRow + " " + nbVal);
            tableName = DBUtil.generateTable(nbCol, nbRow, nbVal);
//            }
            System.out.println("Voulez vous recommencer ? (Y/n)");
            YN = new Scanner(System.in).next();
            onceAgain = YN.equals("Y");
        }while(onceAgain);
    }
}
