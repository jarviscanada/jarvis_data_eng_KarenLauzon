package codingChallenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LetterAndNumber {
    public static void main(String[] args) {
        System.out.println(PrintIndex("abcdefABCDE"));
    }
    public static String PrintIndex(String string1){
        char[] chars = string1.toCharArray();
        Map<Character, Integer> index = new HashMap<Character, Integer>();
        char lLetter = 'a';
        char uLetter = 'A';
        StringBuilder string2 = new StringBuilder();
        for (int n =1; n<27; n++){
            index.put(lLetter,n);
            lLetter++;
        }
        for (int n =27; n<54; n++){
            index.put(uLetter,n);
            uLetter++;
        }
        for(char char1: chars){
            string2.append(char1);
            string2.append(index.get(char1));
        }
        return String.valueOf(string2);

    }
}
