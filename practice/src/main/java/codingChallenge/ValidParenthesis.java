package codingChallenge;

public class ValidParenthesis {
    public static void main(String[] args) {

    }
    public boolean isValid(String s) {
        String[] c = s.split("");
        if(c.length %2 != 0){return false;}

        for (int n = 0; n < c.length; n++){
            if(c[n].matches("\\(")){
                return c[n+1].matches("\\)");
            }
            if(c[n].matches("\\{")){
                return c[n+1].matches("\\}");
            }
            if(c[n].matches("\\[")){
                return c[n+1].matches("\\]");
            }
        }
        return false;
    }
}
