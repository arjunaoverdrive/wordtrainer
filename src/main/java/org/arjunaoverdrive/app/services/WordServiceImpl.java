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

    private List<Word> getProblematicWordsFromAllSets(Integer lang) {
        List<Word> problematicWords = lang == 0 ?
                wordRepository.findAllBySrcRateLessThanAndWordSet_SrcTimesPracticedGreaterThan(0.92f, 0) :
                wordRepository.findAllByTrgtRateLessThanAndWordSet_TargetTimesPracticedGreaterThan(0.92f, 0);
        return new ArrayList<>(problematicWords);
    }

    public List<Word> findProblematicWordsForSet(WordSet set, Integer lang) {
        List<Word> words = lang == 0 ?
                wordRepository.findAllByWordSetAndSrcRateLessThanAndWordSet_SrcTimesPracticedGreaterThan(set, 0.92f, 0) :
                wordRepository.findAllByWordSetAndTrgtRateLessThanAndWordSet_TargetTimesPracticedGreaterThan(set, 0.92f, 0);
        return words;
    }

    @Override
    public List<Word> getLang2Words(Integer setId, Integer lang) {
        if(setId != null){
            return getLang2WordsForSet(setId, lang);
        }
        List<Word> srcWordsWhereRateLessThanNormal =
                wordRepository.findAllBySrcRateLessThanAndWordSet_SrcTimesPracticedGreaterThan(0.92f, 0);
        List<Word> trgtWordsWhereRateLessThanNormal =
                wordRepository.findAllByTrgtRateLessThanAndWordSet_TargetTimesPracticedGreaterThan(0.92f, 0);

        List<Word> words = getUniqueWords(lang, srcWordsWhereRateLessThanNormal, trgtWordsWhereRateLessThanNormal);
        return new ArrayList<>(words);
    }

    private List<Word> getUniqueWords(Integer lang, List<Word> srcWordsWhereRateLessThanNormal, List<Word> trgtWordsWhereRateLessThanNormal) {
        List<Word>words = new ArrayList<>();
        if (lang == 0){
            srcWordsWhereRateLessThanNormal.stream().filter(w -> !trgtWordsWhereRateLessThanNormal.contains(w)).forEach(words::add);
        } else {
            trgtWordsWhereRateLessThanNormal.stream().filter(w -> !srcWordsWhereRateLessThanNormal.contains(w)).forEach(words::add);
        }
        return words;
    }

    private List<Word> getLang2WordsForSet(Integer setId, Integer lang) {
        WordSet set = wordSetService.findById(setId);
        List<Word> words = getUniqueWordsForSet(set, lang);
        return words;
    }

    private List<Word> getUniqueWordsForSet(WordSet set, Integer lang) {
        List<Word> allByWordSetAndSrcRateLessThanAndWordSet_srcTimesPracticedGreaterThan =
                wordRepository.findAllByWordSetAndSrcRateLessThanAndWordSet_SrcTimesPracticedGreaterThan(set, 0.92f, 0);
        List<Word> allByWordSetAndTrgtRateLessThanAndWordSet_targetTimesPracticedGreaterThan =
                wordRepository.findAllByWordSetAndTrgtRateLessThanAndWordSet_TargetTimesPracticedGreaterThan(set, 0.92f, 0);
        return getUniqueWords(lang, allByWordSetAndSrcRateLessThanAndWordSet_srcTimesPracticedGreaterThan,
                allByWordSetAndTrgtRateLessThanAndWordSet_targetTimesPracticedGreaterThan);
    }


}
