package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.web.dto.statistics.WordSetStatsDto;
import org.arjunaoverdrive.app.web.dto.user.UserStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final WordSetStatsService wordSetStatsService;
    private final WordSetService wordSetService;

    @Autowired
    public UserStatisticsServiceImpl(WordSetStatsService wordSetStatsService, WordSetService wordSetService) {
        this.wordSetStatsService = wordSetStatsService;
        this.wordSetService = wordSetService;
    }

    @Override
    public UserStatsDto getStatistics(User user) {
        UserStatsDto userStats = new UserStatsDto();

        Set<WordSetStats> wordSetStatsForUser = wordSetStatsService.getWordSetStatsForUser(user);

        Set<WordSetStatsDto> practicedSetsStats = createWordSetStatsDtos(wordSetStatsForUser);
        userStats.setPracticedStats(practicedSetsStats);

        Set<WordSet> wordSetsCreatedByUser = wordSetService.findAllByCreatedBy(user);
        userStats.setCreatedSets(wordSetsCreatedByUser);

        return userStats;
    }

    private Set<WordSetStatsDto> createWordSetStatsDtos(Set<WordSetStats> wordSetStats) {
        Map<WordSet, List<WordSetStats>> wordSetsToWordSetStatsList =
                getWordSet2WordSetStatsListMap(wordSetStats);
        return populateWordSetStats( wordSetsToWordSetStatsList);
    }

    private Set<WordSetStatsDto> populateWordSetStats(Map<WordSet, List<WordSetStats>> wordSetsToWordSetStatsList) {
        Set<WordSetStatsDto> practicedSetStats = new HashSet<>();
        Set<WordSet> wordSets = wordSetsToWordSetStatsList.keySet();
        for (WordSet ws : wordSets) {
            WordSetStatsDto wss = new WordSetStatsDto();
            populateWordSetStatsDtoDataFields(ws, wss);
            populateStatisticsFields(wss, wordSetsToWordSetStatsList.get(ws));
            practicedSetStats.add(wss);
        }
        return practicedSetStats;
    }

    private void populateWordSetStatsDtoDataFields(WordSet ws, WordSetStatsDto wss) {
        wss.setId(ws.getId());
        wss.setSetName(ws.getName());
        wss.setSourceLanguage(ws.getSourceLanguage().getLanguage());
        wss.setTargetLanguage(ws.getTargetLanguage().getLanguage());
    }


    private void populateStatisticsFields(WordSetStatsDto wss, List<WordSetStats> values) {
        String sourceLanguage = wss.getSourceLanguage();
        String targetLanguage = wss.getTargetLanguage();

        setBestResult(wss, values, sourceLanguage, targetLanguage);
        setLastPracticed(wss, values, sourceLanguage, targetLanguage);
        setAverageResult(wss, values, sourceLanguage, targetLanguage);
        setTimesPracticed(wss, values, sourceLanguage, targetLanguage);
    }

    private void setTimesPracticed(WordSetStatsDto wss, List<WordSetStats> values, String sourceLanguage, String targetLanguage) {


        wss.setTimesPracticedSource(calculateTimesPracticedForLang(values, sourceLanguage));
        wss.setTimesPracticedTarget(calculateTimesPracticedForLang(values, targetLanguage));
    }

    private void setAverageResult(WordSetStatsDto wss, List<WordSetStats> values, String sourceLanguage, String targetLanguage) {
        wss.setAverageResultSourceLang(calculateAverageResultForLang(values, sourceLanguage));
        wss.setAverageResultTargetLang(calculateAverageResultForLang(values, targetLanguage));
    }

    private void setLastPracticed(WordSetStatsDto wss, List<WordSetStats> values, String sourceLanguage, String targetLanguage) {
        wss.setLastPracticedSourceLang(getLastPracticedForLang(values, sourceLanguage));
        wss.setLastPracticedTargetLang(getLastPracticedForLang(values, targetLanguage));
    }

    private void setBestResult(WordSetStatsDto wss, List<WordSetStats> values, String sourceLanguage, String targetLanguage) {
        wss.setBestResultSourceLang(calculateBestResultForLang(values, sourceLanguage));
        wss.setBestResultTargetLang(calculateBestResultForLang(values, targetLanguage));
    }

    private int calculateTimesPracticedForLang(List<WordSetStats> values, String lang) {
        return (int) values
                .stream()
                .filter(setStats -> setStats.getLanguage().equals(lang))
                .count();
    }


    private float calculateAverageResultForLang(List<WordSetStats> values, String language) {
        int timesPracticed = calculateTimesPracticedForLang(values, language);
        return timesPracticed == 0 ? (float) timesPracticed : values.stream()
                .filter(setStats -> setStats.getLanguage().equals(language))
                .map(WordSetStats::getAccuracy)
                .reduce(Float::sum)
                .get() /
                timesPracticed;
    }

    private Timestamp getLastPracticedForLang( List<WordSetStats> values, String language) {
        int timesPracticed = calculateTimesPracticedForLang(values, language);
        return timesPracticed == 0 ? null : values
                .stream()
                .filter(setStats -> setStats.getLanguage().equals(language))
                .max(Comparator.comparing(WordSetStats::getPracticedAt))
                .get()
                .getPracticedAt();
    }

    private float calculateBestResultForLang(List<WordSetStats> values, String language) {
        Optional<WordSetStats> bestResult =  values
                .stream()
                .filter(setStats -> setStats.getLanguage().equals(language))
                .max(Comparator.comparing(WordSetStats::getAccuracy));
        return bestResult.isEmpty() ? 0f : bestResult.get().getAccuracy();
    }

    private Map<WordSet, List<WordSetStats>> getWordSet2WordSetStatsListMap(Set<WordSetStats> wordSetStatsForUser) {
        return wordSetStatsForUser.stream()
                .collect(Collectors.groupingBy(WordSetStats::getWordSet));
    }
}
