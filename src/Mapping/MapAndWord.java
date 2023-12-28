package Mapping;

import java.util.Map;

public class MapAndWord {
    private String word;

    private Map<String, Integer> wordCountMap;

    public MapAndWord(String word, Map<String, Integer> wordCountMap){
        this.word = word;
        this.wordCountMap = wordCountMap;
    }

    public Map<String, Integer> getMap(){
        return wordCountMap;
    }
    public String getWord(){
        return word;
    }
}
