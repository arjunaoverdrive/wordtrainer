package org.arjunaoverdrive.app.services;

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
import java.util.stream.Collectors;

@Service
public class SetResultServiceImpl implements SetResultService{

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

    private void updateWordSet(WordSet set, ResultDto result) {

        updateLastPracticed(set);
        updateAccuracy(result, set);
        updateTimesPracticed(set);
        setService.update(set);
    }

    private void updateLastPracticed(WordSet set) {
        Timestamp instant = new Timestamp(System.currentTimeMillis());
        set.setLastPracticed(instant);
    }

    private void updateTimesPracticed(WordSet set) {
        int timesPracticed = set.getTimesPracticed();
        timesPracticed++;
        set.setTimesPracticed(timesPracticed);
    }

    private void updateAccuracy(ResultDto result, WordSet set) {
        float accuracy = calculateAccuracy(result.getWordResults());
        accuracy += set.getAccuracy();
        set.setAccuracy(accuracy);
    }

    private float calculateAccuracy(List<WordRes> wordResults) {
        int wordsCount = wordResults.size();
        int sum = wordResults.stream()
                .map(WordRes::getAttempts)
                .reduce(Integer::sum)
                .get();
        return (float) sum/wordsCount;
    }

    private void updateWords(WordSet set, ResultDto result) {
        List<WordRes>wordResults = result.getWordResults();
        List<String>wordsFromResults = wordResults.stream().map(WordRes::getWord).collect(Collectors.toList());

        List<Word> wordList = wordService.findAllBySet(set);

        Map<String, Word> string2word = new HashMap<>();
        wordList.forEach(w -> string2word.put(w.getWord(), w));

        updateCorrectness(wordResults, string2word);
        wordService.saveAll(string2word.values());
    }

    private void updateCorrectness(List<WordRes> wordResults, Map<String, Word> string2word) {
        for(WordRes wr : wordResults){
            Word w = string2word.get(wr.getWord());
            int correctness = w.getCorrectness() + wr.getAttempts();
            w.setCorrectness(correctness);
        }
    }
}
