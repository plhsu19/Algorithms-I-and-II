/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 01.05.2020
 *  Description: Hash Table interview question
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class OlympicAthlete {

    private int athleteAge;

    public OlympicAthlete(int age) {
        athleteAge = age;
    }

    /*
    public int hashCode() {
        Integer temp = athleteAge;
        return temp.hashCode();
    }*/

    public String toString() {
        String AO = "age: " + athleteAge;
        return AO;
    }

    /*
    public boolean equals(Object obj) {
        StdOut.println("enter inherited equals()");
        return super.equals(obj);
    } */


    public boolean equals(Object object) {
        StdOut.println("enter customized equals()");
        if (this == object) return true;
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        OlympicAthlete that = (OlympicAthlete) object;
        if (this.hashCode() == that.hashCode()) return true;
        return false;
    }

    public static void main(String[] args) {
        HashMap<OlympicAthlete, String> testHashMap = new HashMap<>();
        testHashMap.put(new OlympicAthlete(23), "Yuri");
        testHashMap.put(new OlympicAthlete(23), "YuriBlack");
        testHashMap.put(new OlympicAthlete(27), "Victor");
        testHashMap.put(new OlympicAthlete(19), "JJ");

        OlympicAthlete Yuri = new OlympicAthlete(23);
        // OlympicAthlete Yurio = new OlympicAthlete(16);


        boolean searchResult3 = testHashMap.containsValue("Yuri");
        boolean searchResult4 = testHashMap.containsValue("YuriBlack");

        String searchResult1 = testHashMap.get(Yuri);
        // boolean searchResult2 = testHashMap.containsKey(Yurio);
        StdOut.println(searchResult1);
        // StdOut.println(searchResult2);
        StdOut.println(searchResult3);
        StdOut.println(searchResult4);


    }
}
