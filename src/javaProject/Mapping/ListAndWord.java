package javaProject.Mapping;

import java.util.List;

public class ListAndWord {
    private String word;

    private List<String> currentList;

    public ListAndWord(String word, List<String> currentList){
        this.word = word;
        this.currentList = currentList;
    }

    public List<String>  getCurrentList(){
        return currentList;
    }
    public String getWord(){
        return word;
    }
}
