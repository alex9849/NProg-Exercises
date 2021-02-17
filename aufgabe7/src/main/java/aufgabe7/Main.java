package aufgabe7;

import util.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    // Initialisierung
    List<String> strings = new ArrayList<>();
    for (int i = 0; i < 100_000; i++) {
      strings.add(Tools.getRandomString(10));
    }

    // Verarbeitung der Liste
    Map<String, List<String>> stringMap = strings.stream().parallel().collect(Collectors.groupingBy(str -> str.substring(0, 1)));

    Map<String, Integer> stringMapCount = new HashMap<>();
    for (String key : stringMap.keySet()) {
      stringMapCount.put(key, stringMap.get(key).size());
    }

    // Variante mit einem Downstream-Collector (geschachtelte Collectoren)
//    Map<String, Long> stringMapCount = strings.stream().parallel()
//                  .collect( Collectors.groupingBy( str -> str.substring(0, 1), Collectors.counting() ) );


    // Ausgabe der Liste
    int linebreak = 0;
    int stringSum = 0;
    for (String key : stringMapCount.keySet()) {
      System.out.print(key + " : " + stringMapCount.get(key) + "   ");
      stringSum += stringMapCount.get(key);
      if ((++linebreak) % 5 == 0)
        System.out.println();
    }

    System.out.println();
    System.out.println("Anzahl der Strings : " + stringSum);
  }

}
