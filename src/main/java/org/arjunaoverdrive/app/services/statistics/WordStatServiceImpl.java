package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.DAO.WordStatRepository;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.model.WordStat;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.services.statistics.WordStatService;
import org.arjunaoverdrive.app.web.DTO.ResultDto;
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
        Set<WordStat>wordStatSet = createWordStats(resultDto, wordSetStats);
        wordStatRepository.saveAll(wordStatSet);
    }

    private Set<WordStat> createWordStats(ResultDto resultDto, WordSetStats wordSetStats) {
        Set<WordStat> wordStatList = new HashSet<>();

        List<Map<String, List<String>>> results = resultDto.getResult();
        int setId = resultDto.getSetId();
        Map<String, Long> words2ids = word2ids(getWords(setId));

        for (Map<String, List<String>> m : results) {
            int attempts = Integer.parseInt(m.keySet().stream().findFirst().get());
            int rate = attempts;
            m.values().forEach(
                    v -> {
                        v.forEach(s -> {
                                    WordStat wordStat = new WordStat();
                                    wordStat.setWordId(words2ids.get(s));
                                    wordStat.setRate(rate);
                                    wordStat.setWordSetStats(wordSetStats);
                                    wordStatList.add(wordStat);
                                }
                        );
                    }
            );
        }
        return wordStatList;
    }

    private List<Word> getWords(int setId) {
        return wordSetService.findById(setId)
                .getWordList();
    }

    private Map<String, Long> word2ids(List<Word> words) {
        Map<String, Long> word2id = words.stream()
                .collect(Collectors.toMap(Word::getWord, Word::getId));
        return word2id;
    }
}
