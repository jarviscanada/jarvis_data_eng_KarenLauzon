package codingChallenge;

public class OnlyDigits {
    public static void main(String[] args) {
        System.out.println(onlyDigits("1234"));
        System.out.println(onlyDigits("123,000"));
        System.out.println(onlyDigits("123abc"));
        System.out.println(onlyDigits("abc"));
        System.out.println(onlyDigits("12345678900987654321"));
    }
    public static boolean onlyDigits(String s){
        return s.matches("^[0-9]+");
    }
}
