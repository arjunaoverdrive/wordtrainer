package org.arjunaoverdrive.app.web.DTO;

import org.arjunaoverdrive.app.model.Word;

import java.util.List;

public class WordsL2List {
    private List<Word> words;

    public WordsL2List() {
    }

    public WordsL2List(List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
