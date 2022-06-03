package codingChallenge;

public class EvenOrOdd {
    public static void main(String[] args) {
        System.out.println(isEven(0));
        System.out.println(isEven(1));
        System.out.println(isEven(2));
        System.out.println(isEven(3));
        System.out.println(isEven(4));
        System.out.println(isEven(5));
        System.out.println(isEven(6));
    }
    public static boolean isEven(int n){
        return n%2==0;
    }
}
