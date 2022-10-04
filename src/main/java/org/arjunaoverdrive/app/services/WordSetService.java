package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.DAO.WordRepository;
import org.arjunaoverdrive.app.DAO.WordSetRepository;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WordSetService {

    @Autowired
    private final WordSetRepository wordSetRepository;
    private final WordRepository wordRepository;

    public WordSetService(WordSetRepository wordSetRepository, WordRepository wordRepository) {
        this.wordSetRepository = wordSetRepository;
        this.wordRepository = wordRepository;
    }

    public List<WordSet> findAll() {
        return wordSetRepository.findAll().isEmpty()? new ArrayList<>() : wordSetRepository.findAll();
    }

    public WordSet findById(Integer id) {
        return Optional.of(wordSetRepository.findById(id)).orElseThrow(() -> new RuntimeException("Set with this id is not found")).get();
    }

    public List<Word> getWordList(Integer id) {
        List<Word> wordList = findById(id).getWordList();
        Collections.shuffle(wordList);
        return wordList;
    }

    public void deleteSet(Integer id) {
        WordSet ws = findById(id);
        wordSetRepository.delete(ws);
    }

    public void save(WordSet wordSet) {
        WordSet set = wordSetRepository.save(wordSet);
        List<Word>wordList = wordSet.getWordList();
        wordList.stream().forEach(word -> word.setWordSet(set)); // TODO: 05.10.2022 handle NPE when saving a set with no words 
        wordRepository.saveAll(wordList);
    }
}
