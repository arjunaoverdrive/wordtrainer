package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.DAO.WordRepository;
import org.arjunaoverdrive.app.DAO.WordSetRepository;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordSetServiceImpl implements WordSetService {

    @Autowired
    private final WordSetRepository wordSetRepository;
    private final WordRepository wordRepository;

    public WordSetServiceImpl(WordSetRepository wordSetRepository, WordRepository wordRepository) {
        this.wordSetRepository = wordSetRepository;
        this.wordRepository = wordRepository;
    }

    public List<WordSet> findAll() {
        return this.wordSetRepository.findAll().isEmpty() ? new ArrayList<>() :
                this.wordSetRepository.findAll()
                        .stream()
                        .sorted(Comparator.comparing(WordSet::getCreatedOn).reversed())
                        .collect(Collectors.toList());
    }

    public WordSet findById(Integer id) {
        return Optional.of(this.wordSetRepository.findById(id)).get()
                .orElseThrow(() -> new RuntimeException("Set with this id is not found"));
    }

    public List<Word> getWordList(Integer id) {
        List<Word> wordList = findById(id).getWordList();
        Collections.shuffle(wordList);
        return wordList;
    }

    public void deleteSet(Integer id) {
        WordSet ws = findById(id);
        this.wordSetRepository.delete(ws);
    }

    @Override
    public List<WordSet> findAllRecent() {
        List<WordSet> res = findAll()
                .stream()
                .sorted(Comparator.comparing(WordSet::getCreatedOn).reversed())
                .collect(Collectors.toList());
        return res.size() < 10 ? res :
                res.stream().limit(10).collect(Collectors.toList());
    }

    public void save(WordSet wordSet) {
        Timestamp createdOn = new Timestamp(System.currentTimeMillis());
        wordSet.setCreatedOn(createdOn);
        WordSet set = this.wordSetRepository.save(wordSet);
        List<Word> wordList = wordSet.getWordList();
        wordList.forEach(word -> word.setWordSet(set));
        this.wordRepository.saveAll(wordList);
    }

    public boolean update(WordSet wordSet) {
        if(wordSet.getWordList() == null){
            deleteSet(wordSet.getId());
            return false;
        }
        List<Word> words = wordSet.getWordList().stream().filter(w -> w.getWord() != null).collect(Collectors.toList());
        wordSet.setWordList(words);
        this.wordSetRepository.save(wordSet);
        return true;
    }

}
