package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.wordset.WordSetService;
import org.arjunaoverdrive.app.web.dto.PracticeSetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PracticeServiceImpl implements PracticeService{

    private final WordSetService wordSetService;

    @Autowired
    public PracticeServiceImpl(WordSetService wordSetService) {
        this.wordSetService = wordSetService;
    }


    public PracticeSetDto getPracticeSetDto(Integer id){
        WordSet wordSet = Optional.ofNullable(wordSetService.findById(id))
                .orElseThrow(() -> new RuntimeException("Set with id" + id + " not found "));
        PracticeSetDto dto = new PracticeSetDto();
        dto.setSourceLanguageLocale(wordSet.getSourceLanguage().getLocale());
        dto.setTargetLanguageLocale(wordSet.getTargetLanguage().getLocale());
        List<Word> wordList = getWords(wordSet);

        dto.setWordList(wordList);
        return dto;
    }

    private List<Word> getWords(WordSet wordSet) {
        List<Word> wordList = wordSet.getWordList();
        Collections.shuffle(wordList);
        return wordList;
    }
}
