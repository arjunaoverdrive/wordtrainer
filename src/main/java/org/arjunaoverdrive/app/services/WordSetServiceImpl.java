package org.arjunaoverdrive.app.services;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.dao.WordRepository;
import org.arjunaoverdrive.app.dao.WordSetRepository;
import org.arjunaoverdrive.app.model.Language;
import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.web.dto.WordSetDto;
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

    public void save(WordSetDto wordSetDto, User user) {
        WordSet set = persistWordSet(wordSetDto, user);
        persistWordList(wordSetDto, set);
    }

    private void persistWordList(WordSetDto wordSetDto, WordSet set) {
        List<Word> wordList = wordSetDto.getWordList();
        wordList.forEach(word -> word.setWordSet(set));
        this.wordRepository.saveAll(wordList);
        log.info("save words set " + set.getId());
    }

    private WordSet persistWordSet(WordSetDto wordSetDto, User user) {
        WordSet wordSet = populateWordSetFields(wordSetDto, user);
        WordSet set = this.wordSetRepository.save(wordSet);
        log.info("save set " + set.getId());
        return set;
    }

    private WordSet populateWordSetFields(WordSetDto wordSetDto, User user) {
        WordSet wordSet = new WordSet();
        wordSet.setName(wordSetDto.getName());
        Timestamp createdOn = new Timestamp(System.currentTimeMillis());
        wordSet.setCreatedAt(createdOn);
        wordSet.setCreatedBy(user);
        Language sourceLang = getLanguage(wordSetDto.getSourceLanguage());
        wordSet.setSourceLanguage(sourceLang);
        Language targetLang = getLanguage(wordSetDto.getTargetLanguage());
        wordSet.setTargetLanguage(targetLang);
        wordSet.setWordList(wordSetDto.getWordList());
        return wordSet;
    }

    // TODO: 17.06.2023 write a utility class to handle language input
    private Language getLanguage(String lang) {
        return Arrays.stream(Language.values())
                .filter(l -> l.getLanguage().equals(lang.toUpperCase()))
                .findFirst()
                .get();
    }

    @Override
    public List<WordSet> findAllRecent(User user) {
        List<WordSet> res = findAll()
                .stream()
                .filter(ws -> ws.getCreatedBy().equals(user))
                .sorted(Comparator.comparing(WordSet::getCreatedAt).reversed())
                .collect(Collectors.toList());
        return res.size() < 10 ? res :
                res.stream().limit(10).collect(Collectors.toList());
    }

    public List<WordSet> findAll() {
        return this.wordSetRepository.findAll().isEmpty() ? new ArrayList<>() :
                this.wordSetRepository.findAll()
                        .stream()
                        .sorted(Comparator.comparing(WordSet::getCreatedAt).reversed())
                        .collect(Collectors.toList());
    }

    public WordSet findById(Integer id) {
        return Optional.of(this.wordSetRepository.findById(id)).get()
                .orElseThrow(() -> new RuntimeException("Set with this id is not found"));
    }



    @Override
    public Set<WordSet> findAllByCreatedBy(User user) {
        return wordSetRepository.findAllByCreatedBy(user);
    }

    public void deleteSet(Integer id, User user) {
        WordSet ws = findById(id);
        if(!ws.getCreatedBy().equals(user)){
            throw new RuntimeException("Cannot delete another user's set");
        }
        this.wordSetRepository.delete(ws);
        log.info("delete set " + id);
    }

    public boolean update(WordSet wordSet, User user) {
        if(!wordSet.getCreatedBy().equals(user)){
            log.info("cannot update another user's set");
            return false;
        }

        if (wordSet.getWordList() == null) {
            deleteSet(wordSet.getId(), user);
            log.info("word list is empty. Delete set " + wordSet.getId());
            return false;
        }

        List<Word> words = updateWords(wordSet);
        this.wordRepository.saveAll(words);

        wordSet.setCreatedBy(user);
        populateWordSetFields(wordSet, words);
        this.wordSetRepository.save(wordSet);

        return true;
    }


    private void populateWordSetFields(WordSet wordSet, List<Word> words) {
        WordSet fromDb = findById(wordSet.getId());
        wordSet.setCreatedAt(fromDb.getCreatedAt());
        wordSet.setWordList(words);
    }

    private List<Word> updateWords(WordSet clientSet) {
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
}
