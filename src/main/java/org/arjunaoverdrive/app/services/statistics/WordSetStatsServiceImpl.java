package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.dao.WordSetStatsRepository;
import org.arjunaoverdrive.app.model.*;
import org.arjunaoverdrive.app.services.wordset.WordSetService;
import org.arjunaoverdrive.app.web.dto.statistics.WordSetDetailedStatsDto;
import org.arjunaoverdrive.app.web.dto.statistics.WordStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordSetStatsServiceImpl implements WordSetStatsService {

    private final WordSetStatsRepository wordSetStatsRepository;
    private final WordSetService wordSetService;

    @Autowired
    public WordSetStatsServiceImpl(WordSetStatsRepository wordSetStatsRepository, WordSetService wordSetService) {
        this.wordSetStatsRepository = wordSetStatsRepository;
        this.wordSetService = wordSetService;
    }

    @Override
    public WordSetStats save(WordSetStats wordSetStats){
        wordSetStatsRepository.save(wordSetStats);
        return wordSetStats;
    }

    @Override
    public Set<WordSetStats> getWordSetStatsForUser(User user) {
        return wordSetStatsRepository.findAllByPracticedBy(user);
    }

    @Override
    public WordSetDetailedStatsDto getWordSetDetailedStats(Integer setId, User user) {
        WordSetDetailedStatsDto wordSetDetailedStatsDto = new WordSetDetailedStatsDto();
        WordSet ws = wordSetService.findById(setId);

        wordSetDetailedStatsDto.setSetId(setId);
        wordSetDetailedStatsDto.setName(ws.getName());

        wordSetDetailedStatsDto.setSourceLanguage(ws.getSourceLanguage().getLanguage());
        wordSetDetailedStatsDto.setTargetLanguage(ws.getTargetLanguage().getLanguage());

        List<WordStatsDto> wordStatsDtos = createWordStatsDtos(ws, user);
        wordSetDetailedStatsDto.setWordStatsDtos(wordStatsDtos);
        calculateSetStatistics(ws, wordSetDetailedStatsDto);
        return wordSetDetailedStatsDto;
    }

    private void calculateSetStatistics(WordSet ws, WordSetDetailedStatsDto wordSetDetailedStatsDto){

        List<WordSetStats> wordSetStats = wordSetStatsRepository.findAllByWordSet(ws);

        WordSetStatsCalculator calculator = new WordSetStatsCalculator(wordSetStats,
                ws.getSourceLanguage().getLanguage(), ws.getTargetLanguage().getLanguage());

        calculator.calculateStatistics(wordSetDetailedStatsDto);
    }


    private List<WordStatsDto> createWordStatsDtos(WordSet ws, User user) {
        List<WordSetStats> wordSetStats = wordSetStatsRepository.findByWordSetAndPracticedBy(ws, user);
        String sourceLanguage = ws.getSourceLanguage().getLanguage();
        String targetLanguage = ws.getTargetLanguage().getLanguage();

        Map<Long, Float> wordIdsToRateSource = getWordIdsToRate(wordSetStats, sourceLanguage);
        Map<Long, Float> wordIdsToRateTarget = getWordIdsToRate(wordSetStats, targetLanguage);

        List<Word> words = getSortedWords(ws);

        List<WordStatsDto> wordStatsDtos = new ArrayList<>();
        for (Word w : words) {
            WordStatsDto wsd = new WordStatsDto();
            wsd.setWord(w.getWord());
            wsd.setTranslation(w.getTranslation());
            wsd.setRateSource(wordIdsToRateSource.computeIfAbsent(w.getId(), k -> 0f));
            wsd.setRateTarget(wordIdsToRateTarget.computeIfAbsent(w.getId(), k -> 0f));
            wordStatsDtos.add(wsd);
        }

        return wordStatsDtos;
    }

    private List<Word> getSortedWords(WordSet ws) {
        List<Word> words = ws.getWordList().stream()
                .sorted(Comparator.comparing(Word::getWord))
                .collect(Collectors.toList());
        return words;
    }

    private Map<Long, Float> getWordIdsToRate(List<WordSetStats> wordSetStats, String lang) {
        List<WordSetStats> wss = getWordSetStatsByLang(wordSetStats, lang);
        List<Set<WordStat>> wordStats = getWordStatSetsList(wss);
        WordRateCalculator rateCalculator = new WordRateCalculator(wordStats);
        Map<Long, Float> wordIdsToRate = rateCalculator.getWordIdsToRate();
        return wordIdsToRate;
    }

    private List<Set<WordStat>> getWordStatSetsList(List<WordSetStats> wss) {
        List<Set<WordStat>> wordStatsSetsList = wss.stream()
                .map(WordSetStats::getWordStats)
                .collect(Collectors.toList());
        return wordStatsSetsList;
    }



    private List<WordSetStats> getWordSetStatsByLang(List<WordSetStats> wordSetStats, String lang) {
        List<WordSetStats> filtered = wordSetStats.stream()
                .filter(wss -> wss.getLanguage().equals(lang))
                .collect(Collectors.toList());
        return filtered;
    }
}
