package codingChallenge;

public class StringToInteger {

    public static void main(String[] args) {
        System.out.println(myAtoi("42"));
        System.out.println(myAtoi("-42"));
        System.out.println(myAtoi("    42"));
        System.out.println(myAtoi("    -42"));
        System.out.println(myAtoi("42 with words"));
        System.out.println(myAtoi("-91283472332"));
    }
    public static int myAtoi(String s) {

        String[] stringArray = s.trim().split("");
        StringBuilder newString = new StringBuilder();
        int newint =0;

        for(String c : stringArray){
            if(c.matches("[0-9]+")){
                newString.append(c);
            }else if(c.matches("[-+]")){

            }
            else break;
        }

        if (newString.length()==0){
            return 0;
        }

        try {
            newint = Integer.parseInt(newString.toString());
        }catch(NumberFormatException e){
            return -2147483648;
        }
        if ( stringArray[0].matches("-")){
            return newint-newint-newint;
        }
        return newint;
    }
}
