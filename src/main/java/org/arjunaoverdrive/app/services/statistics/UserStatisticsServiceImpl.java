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

        Set<WordSetStatsDto> practicedSetsStats = getPracticedSetsStats(wordSetStatsForUser);
        userStats.setPracticedStats(practicedSetsStats);

        Set<WordSet> wordSetsCreatedByUser = wordSetService.findAllByCreatedBy(user);
        userStats.setCreatedSets(wordSetsCreatedByUser);

        return userStats;
    }

    private Set<WordSetStatsDto> getPracticedSetsStats(Set<WordSetStats> wordSetStatsForUser) {
        Set<WordSetStatsDto> practicedSetStats = new HashSet<>();
        createWordSetStatsDtos(practicedSetStats, wordSetStatsForUser);
        return practicedSetStats;
    }

    private void createWordSetStatsDtos(Set<WordSetStatsDto> practicedSetStats, Set<WordSetStats> wordSetStats) {
        Map<Integer, List<WordSetStats>> wordSetIdsToWordSetStatsList = getWordSet2WordSetStatsListMap(wordSetStats);
        populateWordSetStats(practicedSetStats, wordSetIdsToWordSetStatsList);
    }

    private void populateWordSetStats(Set<WordSetStatsDto> practicedSetStats, Map<Integer, List<WordSetStats>> wordSetIdsToWordSetStatsList) {
        Set<Integer> ids = wordSetIdsToWordSetStatsList.keySet();
        for (int id : ids) {
            WordSetStatsDto wss = new WordSetStatsDto();
            buildWordSetStats(wordSetIdsToWordSetStatsList, id, wss);
            practicedSetStats.add(wss);
        }
    }

    private void buildWordSetStats(Map<Integer, List<WordSetStats>> wordSetIdsToWordSetStatsList, int id, WordSetStatsDto wss) {
        wss.setId(id);
        List<WordSetStats> values = wordSetIdsToWordSetStatsList.get(id);
        populateSetDataFields(id, wss);
//        wss.setSetName(getSetName(id));
        populateStatisticsFields(wss, values);
    }

    private void populateStatisticsFields(WordSetStatsDto wss, List<WordSetStats> values) {
        setBestResult(wss, values);
        setLastPracticed(wss, values);
        setAverageResult(wss, values);
        setTimesPracticed(wss, values);
    }

    private void setTimesPracticed(WordSetStatsDto wss, List<WordSetStats> values) {
        wss.setTimesPracticedSource(calculateTimesPracticedForLang(values, 1));
        wss.setTimesPracticedTarget(calculateTimesPracticedForLang(values, 0));
    }

    private void setAverageResult(WordSetStatsDto wss, List<WordSetStats> values) {
        wss.setAverageResultSourceLang(calculateAverageResultForLang(values, 1));
        wss.setAverageResultTargetLang(calculateAverageResultForLang(values, 0));
    }

    private void setLastPracticed(WordSetStatsDto wss, List<WordSetStats> values) {
        wss.setLastPracticedSourceLang(getLastPracticedForLang(values, 1));
        wss.setLastPracticedTargetLang(getLastPracticedForLang(values, 0));
    }

    private void setBestResult(WordSetStatsDto wss, List<WordSetStats> values) {
        wss.setBestResultSourceLang(calculateBestResultForLang(values, 1));
        wss.setBestResultTargetLang(calculateBestResultForLang(values, 0));
    }

    private int calculateTimesPracticedForLang(List<WordSetStats> values, int lang) {
        return (int) values
                .stream()
                .filter(setStats -> setStats.getLang() == lang)
                .count();
    }

    private void populateSetDataFields(int id, WordSetStatsDto wss) {
        WordSet ws = wordSetService.findById(id);
        wss.setSetName(ws.getName());
        wss.setSourceLanguage(ws.getSourceLanguage());
        wss.setTargetLanguage(ws.getTargetLanguage());
    }

    private String getSetName(int id) {
        return wordSetService.findById(id).getName();
    }

    private float calculateAverageResultForLang(List<WordSetStats> values, int lang) {
        int timesPracticed = calculateTimesPracticedForLang(values, lang);
        return timesPracticed == 0 ? (float) timesPracticed : values.stream()
                .filter(setStats -> setStats.getLang() == lang)
                .map(WordSetStats::getAccuracy)
                .reduce(Float::sum)
                .get() /
                timesPracticed;
    }

    private Timestamp getLastPracticedForLang( List<WordSetStats> values, int lang) {
        int timesPracticed = calculateTimesPracticedForLang(values, lang);
        return timesPracticed == 0 ? null : values
                .stream()
                .filter(setStats -> setStats.getLang() == lang)
                .max(Comparator.comparing(WordSetStats::getPracticedAt))
                .get()
                .getPracticedAt();
    }

    private float calculateBestResultForLang(List<WordSetStats> values, int lang) {
        Optional<WordSetStats> bestResult =  values
                .stream()
                .filter(setStats -> setStats.getLang() == lang)
                .max(Comparator.comparing(WordSetStats::getAccuracy));
        return bestResult.isEmpty() ? 0f : bestResult.get().getAccuracy();
    }

    private Map<Integer, List<WordSetStats>> getWordSet2WordSetStatsListMap(Set<WordSetStats> wordSetStatsForUser) {
        return wordSetStatsForUser.stream()
                .collect(Collectors.groupingBy(wss -> wss.getWordSet().getId()));
    }
}
