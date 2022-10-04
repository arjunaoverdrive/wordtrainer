package org.arjunaoverdrive.app.DTO;

import org.arjunaoverdrive.app.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordsCreationDto {
    private Integer id;
    private String name;
    private List<Word>wordList = new ArrayList<>();

    public void addWord(Word word){
        this.wordList.add(word);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }
}
