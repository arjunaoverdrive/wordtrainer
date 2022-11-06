package org.arjunaoverdrive.app.services;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DTO.ResultDto;
import org.arjunaoverdrive.app.DTO.WordRes;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SetResultServiceImpl implements SetResultService {

    private final WordSetService setService;
    private final WordService wordService;

    @Autowired
    public SetResultServiceImpl(WordSetService setService, WordService wordService) {
        this.setService = setService;
        this.wordService = wordService;
    }

    @Override
    public void save(Integer id, ResultDto result) {
        WordSet set = setService.findById(id);
        updateWordSet(set, result);
        updateWords(set, result);
    }

    @Override
    public void updateWordsRates(List<WordRes> wordResultsList) {

    }

    private void updateWordSet(WordSet set, ResultDto result) {
        updateLastPracticed(set);
        updateTimesPracticed(result, set);
        updateAccuracy(result, set);
        setService.update(set);
        log.info("update set " + set.getId());
    }

    private void updateLastPracticed(WordSet set) {
        Timestamp instant = new Timestamp(System.currentTimeMillis());
        set.setLastPracticed(instant);
        log.info("update set last practiced: " + set.getId());
    }

    private void updateTimesPracticed(ResultDto result, WordSet set) {
        if (result.isLang()) {
            int timesPracticed = set.getSrcTimesPracticed();
            timesPracticed++;
            set.setSrcTimesPracticed(timesPracticed);
            log.info("update set src times practiced " + set.getId());
        } else
            updateTargetLangTimesPracticed(set);
    }

    private void updateTargetLangTimesPracticed(WordSet set) {
        int targetLangTimesPracticed = set.getTargetTimesPracticed();
        targetLangTimesPracticed++;
        set.setTargetTimesPracticed(targetLangTimesPracticed);
        log.info("update set trg times practiced " + set.getId());
    }

    private void updateAccuracy(ResultDto result, WordSet set) {
        if (result.isLang()) {
            float accuracy = calculateAccuracy(result.getWordResults());
            accuracy += set.getSrcLangAccuracy();
            set.setSrcLangAccuracy(accuracy);
            log.info("update set src lang accuracy " + set.getId() + ". Source language accuracy " + accuracy);
        } else updateTargetLangAccuracy(result, set);
    }

    private void updateTargetLangAccuracy(ResultDto result, WordSet set) {
        float targetLangAccuracy = calculateAccuracy(result.getWordResults());
        targetLangAccuracy += set.getTargetLangAccuracy();
        set.setTargetLangAccuracy(targetLangAccuracy);
        log.info("update set target lang accuracy " + set.getId() + ". Target language accuracy " +targetLangAccuracy);
    }

    private float calculateAccuracy(List<WordRes> wordResults) {
        int wordsCount = wordResults.size();
        int sum = wordResults.stream()
                .map(WordRes::getAttempts)
                .reduce(Integer::sum)
                .get();
        return (float) wordsCount / sum;
    }

    private void updateWords(WordSet set, ResultDto result) {
        List<WordRes> wordResults = result.getWordResults();
        List<Word> wordList = wordService.findAllBySet(set);
        int timesPracticed = result.isLang() ? set.getSrcTimesPracticed() : set.getTargetTimesPracticed();
        Map<String, Word> string2word = new HashMap<>();
        if (result.isLang()) {
            wordList.forEach(w -> string2word.put(w.getWord(), w));
            updateSrcRate(wordResults, string2word, timesPracticed);
        } else {
            wordList.forEach(w -> string2word.put(w.getTranslation(), w));
            updateTrgtRate(wordResults, string2word, timesPracticed);
        }
        wordService.saveAll(string2word.values());
        log.info("update set words " + set.getId());
    }

    private void updateTrgtRate(List<WordRes> wordResults, Map<String, Word> string2word, int timesPracticed) {
        for (WordRes wr : wordResults) {
            Word w = string2word.get(wr.getWord());
            float targetRate =
                    (w.getTrgtRate() + 1/(float) wr.getAttempts())/timesPracticed;
            w.setTrgtRate(targetRate);
            log.info("update set words translation rate " + w.getWord() + " : " + targetRate);
        }
    }

    private void updateSrcRate(List<WordRes> wordResults, Map<String, Word> string2word, int timesPracticed) {
        for (WordRes wr : wordResults) {
            Word w = string2word.get(wr.getWord());
            float srcRate =  (w.getSrcRate() + 1 /(float) wr.getAttempts())/ timesPracticed;
            w.setSrcRate(srcRate);
            log.info("update set words rate " + w.getWord() + " : " + srcRate);
        }
    }
}
