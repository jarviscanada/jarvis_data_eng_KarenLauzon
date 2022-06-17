package codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class CompareMaps {
    public static void main(String[] args) {
        Map<String, String> capCities = new HashMap<String, String>();
        Map<String, String> capCities2 = new HashMap<String, String>();
        Map<String, String> ages = new HashMap<String, String>();

        capCities.put("England","London");
        capCities.put("Canada","Ottawa");
        capCities.put("USA","Washington DC");

        capCities2.putAll(capCities);

        ages.put("Patrick","32");
        ages.put("Conner","23");
        ages.put("Neil","29");

        System.out.println(compareMaps(capCities,capCities2));
        System.out.println(compareMaps(capCities,ages));
        System.out.println(compareMaps(capCities2,capCities));
        System.out.println(compareMaps(capCities2,ages));
        System.out.println(compareMaps(ages,capCities));
        System.out.println(compareMaps(ages,capCities2));
    }
    public static <K,V> boolean compareMaps(Map<K, V> m1, Map<K, V> m2){
        return m1.equals(m2);
    }
}
