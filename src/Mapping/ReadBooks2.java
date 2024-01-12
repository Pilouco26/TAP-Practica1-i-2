package Mapping;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ReadBooks2 implements ReadBook{
    @Override
    public Object filterPonctuation(Object args) {
        ListAndWord listAndWord = (ListAndWord) args;
        List<String> currentList = listAndWord.getCurrentList();
        String word = listAndWord.getWord();
        String lowercaseWord = word.toLowerCase();
        String filteredWord = lowercaseWord.replaceAll("[,.\\-_!?;:”)—“'(‘™’\"0123456789£•|]", "");

        currentList.add(filteredWord);

        return 0;
    }


    @Override
    public Object putMap(Object args) {

        MapAndWord mapAndWord = (MapAndWord) args;
        Map<String, Integer> wordCountMap = mapAndWord.getMap();
        String currentWord = mapAndWord.getWord();
        if (!wordCountMap.containsKey(currentWord)) {
            wordCountMap.put(currentWord, 1);
        } else {
            wordCountMap.put(currentWord, wordCountMap.get(currentWord) + 1);
        }
        return 0;
    }

    @Override
    public Object printMapCount(Object args) {
        Map<String, Integer> map = (Map<String, Integer>) args;


        // Create a new TreeMap to sort the word count map by word
        TreeMap<String, Integer> sortedWordCountMap = new TreeMap<>(map);

        // Print the sorted word count map
        for (Map.Entry<String, Integer> entry : sortedWordCountMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        return 0;
    }

}
