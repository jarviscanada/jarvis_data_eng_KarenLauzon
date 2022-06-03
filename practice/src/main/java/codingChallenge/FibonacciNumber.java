package codingChallenge;

public class FibonacciNumber {

    public static void main(String[] args) {
        System.out.println(fib(0));
        System.out.println(fib(1));
        System.out.println(fib(2));
        System.out.println(fib(3));
        System.out.println(fib(4));
        System.out.println(fib(5));

    }
    public static int fib(int n) {
        int first =0;
        int second= 1;
        int next = second;

        for(int i = 1; i< n; i++){
            next = first + second;
            first =second;
            second = next;
        }
        if(n==0){return 0;}
        return next;
    }
}
