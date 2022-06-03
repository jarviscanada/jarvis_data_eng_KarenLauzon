package codingChallenge;

import java.util.Arrays;

public class ValidAnagram {
    public static void main(String[] args) {
        System.out.println(isAnagram("anagram","nagaram"));
        System.out.println(isAnagram("rat","car"));
        System.out.println(isAnagram("rat","tar"));
        System.out.println();
    }
    public static boolean isAnagram(String s, String t) {
        String[] sList = s.split("");
        String[] tList = t.split("");
        Arrays.sort(sList);
        Arrays.sort(tList);

        boolean matches = false;
        if (sList.length != tList.length) return false;
        for(int n = 0; n< sList.length; n++){
            matches = sList[n].matches(tList[n]);
            if (!matches) return false;
        }
        return matches;
    }
}
