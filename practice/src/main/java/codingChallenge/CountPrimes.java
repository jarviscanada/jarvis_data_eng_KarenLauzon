package codingChallenge;

public class CountPrimes {
    public static void main(String[] args) {
        System.out.println(countPrimes(10));
    }
    public static int countPrimes(int n) {
        int numOfPrimes=0;
        boolean isdiv = false;

        for(int count =2; count <n; count++){
            if(count ==2){
                numOfPrimes++;
            }
            if (count%2==0 || count %3==0){
                isdiv=true;
            }
            for (int div = 2; div < count; div++) {
                if (count%div == 0){
                    isdiv =true;
                    break;
                }else{
                    isdiv =false;
                }
            }
            if (!isdiv) numOfPrimes ++;
        }
        return numOfPrimes;
    }
}
