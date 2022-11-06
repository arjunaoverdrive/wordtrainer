package org.arjunaoverdrive.app.services;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DAO.WordRepository;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final WordSetService wordSetService;

    @Autowired
    public WordServiceImpl(WordRepository wordRepository, WordSetService wordSetService) {
        this.wordRepository = wordRepository;
        this.wordSetService = wordSetService;
    }

    @Override
    public void deleteWord(Long id) {
        Word word = this.wordRepository.findById(id).get();
        this.wordRepository.delete(word);
        log.info("delete word " + id);
    }

    @Override
    public void saveAll(Iterable<Word> words) {
        this.wordRepository.saveAll(words);
        log.info("saving all words");
    }

    @Override
    public List<Word> findAllBySet(WordSet set) {
        return this.wordRepository.findAllByWordSet(set);
    }

    @Override
    public List<Word> getWordListWithProblematicWords(Integer setId, Integer lang) {
        if (setId == 0) {
            return getProblematicWordsFromAllSets(lang);
        }
        WordSet set = wordSetService.findById(setId);
        List<Word> problematicWords = findProblematicWordsForSet(set, lang);
        return new ArrayList<>(problematicWords);
    }

    @Override
    public List<Word> getLang2Words(Integer setId, Integer lang) {
        if(setId != null){
            return getLang2WordsForSet(setId, lang);
        }
        List<Word> words = lang == 0 ? wordRepository.findAllBySrcRateLessThanAndWordSet_SrcTimesPracticedGreaterThan(1f, 0) :
                wordRepository.findAllByTrgtRateLessThanAndWordSet_TargetTimesPracticedGreaterThan(1f, 0);
        return new ArrayList<>(words);
    }

    private List<Word> getLang2WordsForSet(Integer setId, Integer lang) {
        WordSet set = wordSetService.findById(setId);
        List<Word> words = findProblematicWordsForSet(set, lang);
        return words;
    }

    private List<Word> getProblematicWordsFromAllSets(Integer lang) {
        List<Word> problematicWords = lang == 0 ?
                wordRepository.findAllBySrcRateLessThanAndWordSet_SrcTimesPracticedGreaterThan(1f, 0) :
                wordRepository.findAllByTrgtRateLessThanAndWordSet_TargetTimesPracticedGreaterThan(1f, 0);
        return new ArrayList<>(problematicWords);
    }

    public List<Word> findProblematicWordsForSet(WordSet set, Integer lang) {
        List<Word> words = lang == 0 ?
                wordRepository.findAllByWordSetAndSrcRateLessThanAndWordSet_SrcTimesPracticedGreaterThan(set, 1f, 0) :
                wordRepository.findAllByWordSetAndTrgtRateLessThanAndWordSet_TargetTimesPracticedGreaterThan(set, 1f, 0);
        return words;
    }
}
