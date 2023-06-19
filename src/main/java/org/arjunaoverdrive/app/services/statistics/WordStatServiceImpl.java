package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.dao.WordStatRepository;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.model.WordStat;
import org.arjunaoverdrive.app.services.wordset.WordSetService;
import org.arjunaoverdrive.app.web.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordStatServiceImpl implements WordStatService {

    private final WordStatRepository wordStatRepository;
    private final WordSetService wordSetService;

    @Autowired
    public WordStatServiceImpl(WordStatRepository wordStatRepository, WordSetService wordSetService) {
        this.wordStatRepository = wordStatRepository;
        this.wordSetService = wordSetService;
    }

    @Override
    public void save(ResultDto resultDto, WordSetStats wordSetStats) {
        Set<WordStat> wordStatSet = createWordStats(resultDto, wordSetStats);
        wordStatRepository.saveAll(wordStatSet);
    }

    @Override
    public Set<WordStat> findByWordSetStats(WordSetStats wordSetStats) {
        return wordStatRepository.findByWordSetStats(wordSetStats);
    }

    private Set<WordStat> createWordStats(ResultDto resultDto, WordSetStats wordSetStats) {
        
        Map<String, List<String>> results = resultDto.getResult();
        int setId = resultDto.getSetId();
        String language = resultDto.getLanguage();

        Map<String, Long> words2ids = word2ids(setId, language);

        Set<WordStat> wordStatSet = populateWordStats(wordSetStats, results, words2ids);
        return wordStatSet;
    }

    private Set<WordStat> populateWordStats(WordSetStats wordSetStats, Map<String, List<String>> results, Map<String, Long> words2ids) {
        Set<WordStat> wordStatSet = new HashSet<>();
        for (Map.Entry<String, List<String>> e : results.entrySet()) {
            int rate = Integer.parseInt(e.getKey());

            e.getValue().forEach(
                    s -> {
                        WordStat wordStat = new WordStat();
                        wordStat.setWordId(words2ids.get(s));
                        wordStat.setRate(rate);
                        wordStat.setWordSetStats(wordSetStats);
                        wordStatSet.add(wordStat);
                    }
            );
        }
        
        return wordStatSet;
    }

    private WordSet getWordSet(int setId){
        return wordSetService.findById(setId);
    }

    private Map<String, Long> word2ids(int setId, String language) {
        WordSet wordSet = getWordSet(setId);
        List<Word> words = wordSet.getWordList();
        String sourceLanguageLocale = wordSet.getSourceLanguage().getLocale();
        return words.stream()
                .collect(Collectors.toMap(language.equals(sourceLanguageLocale) ? Word::getWord : Word::getTranslation, Word::getId));
    }
}
