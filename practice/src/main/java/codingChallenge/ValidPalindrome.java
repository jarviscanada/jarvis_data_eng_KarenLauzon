package codingChallenge;

import java.util.ArrayList;

public class ValidPalindrome {

    public static void main(String[] args) {
        System.out.println(isPalindrome("aba"));
        System.out.println(isPalindrome("raceCar"));
        System.out.println(isPalindrome("race Car"));
        System.out.println(isPalindrome("race a Car"));
        System.out.println(isPalindrome("1221"));
        System.out.println(isPalindrome("122roor221"));
        System.out.println(isPalindrome("122poor221"));
    }

    public static boolean isPalindrome(String s){
        ArrayList<String> original = new ArrayList<>();
        ArrayList<String> reverse = new ArrayList<>();

        for(String c : s.split("")){
            if(c.toLowerCase().matches("[a-z0-9]")){
                original.add(c.toLowerCase());
            }
        }

        for(int n = original.size()-1; n>=0;n--){
            reverse.add(original.get(n));
        }

        return original.equals(reverse);
    }
}
