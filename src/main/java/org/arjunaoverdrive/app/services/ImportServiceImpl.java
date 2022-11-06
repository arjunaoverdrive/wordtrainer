package org.arjunaoverdrive.app.services;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DAO.WordRepository;
import org.arjunaoverdrive.app.DAO.WordSetRepository;
import org.arjunaoverdrive.app.DTO.ImportDto;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ImportServiceImpl implements ImportService{

    private final WordRepository wordRepository;
    private final WordSetRepository wordSetRepository;
    @Autowired
    public ImportServiceImpl(WordRepository wordRepository, WordSetRepository wordSetRepository) {
        this.wordRepository = wordRepository;
        this.wordSetRepository = wordSetRepository;
    }

    @Override
    public void importSet(ImportDto importDto) {
        String words = importDto.getWords().trim();
        String delimiter = importDto.getDelimiter();
        String name = importDto.getName();
        WordSet set = new WordSet();
        set.setName(name);
        Timestamp creationDate = new Timestamp(System.currentTimeMillis());
        set.setCreatedOn(creationDate);
        wordSetRepository.save(set);
        log.info("save set " + set.getId());
        List<Word> wordList = parseWords(words, delimiter);
        wordList.forEach(word -> word.setWordSet(set));
        wordRepository.saveAll(wordList);
        log.info("save words " + wordList.size());
    }

    private List<Word> parseWords(String words, String delimiter) {
        List<Word> wordList = new ArrayList<>();
        String[] wordPairs = words.split("\n");
        for (String pair : wordPairs) {
            String[] wordPair = pair.split(delimiter);
            Word word = new Word();
            word.setWord(wordPair[0].trim());
            word.setTranslation(wordPair[1].trim());
            wordList.add(word);
        }
        return wordList;
    }

}
