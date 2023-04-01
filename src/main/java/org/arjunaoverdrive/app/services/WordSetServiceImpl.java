package org.arjunaoverdrive.app.services;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DAO.WordRepository;
import org.arjunaoverdrive.app.DAO.WordSetRepository;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.services.WordSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.info("delete set " + id);
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
        log.info("save set " + set.getId());

        List<Word> wordList = wordSet.getWordList();
        wordList.forEach(word -> word.setWordSet(set));
        this.wordRepository.saveAll(wordList);
        log.info("save words set " + set.getId());
    }

    public void updateStats(WordSet wordSet) {
        List<Word> words = wordSet.getWordList().stream().filter(w -> w.getWord() != null).collect(Collectors.toList());
        wordSet.setWordList(words);
        this.wordSetRepository.save(wordSet);
        log.info("update set statistics" + wordSet.getId());
    }

    public boolean update(WordSet wordSet) {
        if (wordSet.getWordList() == null) {
            deleteSet(wordSet.getId());
            log.info("word list is empty. Delete set " + wordSet.getId());
            return false;
        }

        List<Word> words = updateWordsStats(wordSet);
        this.wordRepository.saveAll(words);

        populateWordSetFields(wordSet, words);
        this.wordSetRepository.save(wordSet);

        return true;

    }

    private void populateWordSetFields(WordSet wordSet, List<Word> words) {
        WordSet fromDb = findById(wordSet.getId());
        wordSet.setCreatedOn(fromDb.getCreatedOn());
        wordSet.setWordList(words);
    }


    private List<Word> updateWordsStats(WordSet clientSet) {
        int setId = clientSet.getId();
        List<Word> dbList = findById(setId).getWordList();
        List<Word> clientList = clientSet.getWordList();

        List<Word> result = new ArrayList<>();
        addNewWordsToResult(clientList, result);

        updateDbWords(dbList, clientList, result);

        return result;
    }

    private void addNewWordsToResult(List<Word> clientList, List<Word> result) {
        List<Word> newWords = clientList.stream().filter(word -> word.getId() == 0).filter(word -> word.getWord() != null).collect(Collectors.toList());
        result.addAll(newWords);
    }

    private void updateDbWords(List<Word> dbList, List<Word> clientList, List<Word> result) {
        Map<String, Word> dbMap = getString2WordMap(dbList);
        Map<String, Word> clientMap = getString2WordMap(clientList);

        Set<String> clientWords = clientMap.keySet();
        for (String s : clientWords) {
            if (result.contains(clientMap.get(s))) continue;

            if (!dbMap.containsKey(s)) {
                result.add(clientMap.get(s));
            } else if (!dbMap.get(s).equals(clientMap.get(s))) {
                result.add(clientMap.get(s));
            } else if (dbMap.get(s).equals(clientMap.get(s))) {
                result.add(dbMap.get(s));
            }
        }
    }

    private Map<String, Word> getString2WordMap(List<Word> wordList) {
        Map<String, Word> res = new HashMap<>();
        wordList.stream().filter(w -> w.getWord() != null).forEach(w -> res.put(w.getWord(), w));
        return res;
    }


    @Override
    public String getSetNameById(Integer setId) {
        return setId == 0 ? "all sets" :
                findById(setId).getName();
    }
}
