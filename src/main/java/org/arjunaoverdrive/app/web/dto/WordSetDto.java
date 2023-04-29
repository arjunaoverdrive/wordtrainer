package org.arjunaoverdrive.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arjunaoverdrive.app.model.Word;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordSetDto {
    private String name;
    private String sourceLanguage;
    private String targetLanguage;
    private List<Word> wordList = new ArrayList<>();

    public void addWord(Word word){
        this.wordList.add(word);
    }

}
