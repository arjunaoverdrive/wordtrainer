package org.arjunaoverdrive.app.DTO;

import org.arjunaoverdrive.app.model.Word;

import java.util.List;

public class MistakenWordsDto {
    List<Word> words;

    public MistakenWordsDto() {
    }

    public MistakenWordsDto(List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
