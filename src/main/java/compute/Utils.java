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
}
