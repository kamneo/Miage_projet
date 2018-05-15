package compute;

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

    public static List<String> allCombinations(int size){
        List<String> res = new ArrayList<String>();
        return comb2("", letters(size), res);
    }

    private static List<String> comb2(String prefix, String s, List<String> res) {
        res.add(prefix);
        for (int i = 0; i < s.length(); i++) {
            comb2(prefix + s.charAt(i), s.substring(i + 1), res);
        }
        return res;
    }

    /*private static List<String> test(String active, List<String> rest, List<String> res) {
        if(active.length() <= 0 && rest.size() <= 0) return null;
        if(rest.size() <= 0)
            res.add(active);
        else{
            String currentActive = active + rest.get(0);
            rest.remove(0);
            test(currentActive, rest, res);
            test(active, rest, res);
        }
        return res;
    }*/
}
