package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSet;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.model.WordStat;
import org.arjunaoverdrive.app.services.wordset.WordSetService;
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
        Map<WordSet, List<WordSetStats>> wordSetToWordSetStatsList =
                getWordSet2WordSetStatsListMap(wordSetStats);

        return buildPracticedSetsStats(wordSetToWordSetStatsList);
    }

    private Set<WordSetStatsDto> buildPracticedSetsStats(Map<WordSet, List<WordSetStats>> wordSetToWordSetStatsList) {

        Set<WordSetStatsDto> practicedSetStats = new HashSet<>();
        Set<WordSet> wordSets = wordSetToWordSetStatsList.keySet();

        for (WordSet wordSet : wordSets) {
            WordSetStatsDto wordSetStatsDto = buildWordSetStatsDto(wordSetToWordSetStatsList, wordSet);
            practicedSetStats.add(wordSetStatsDto);
        }
        return practicedSetStats;
    }

    private WordSetStatsDto buildWordSetStatsDto(Map<WordSet, List<WordSetStats>> wordSetToWordSetStatsList, WordSet wordSet) {
        WordSetStatsDto wordSetStatsDto = new WordSetStatsDto();

        wordSetStatsDto.setId(wordSet.getId());
        wordSetStatsDto.setSetName(wordSet.getName());

        String sourceLanguage = wordSet.getSourceLanguage().getLanguage();
        String targetLanguage = wordSet.getTargetLanguage().getLanguage();


        wordSetStatsDto.setSourceLanguage(sourceLanguage);
        wordSetStatsDto.setTargetLanguage(targetLanguage);
        List<WordSetStats> wordSetStatsGroupedByWordSet = wordSetToWordSetStatsList.get(wordSet);

        wordSetStatsDto.setProgressSource(calculateProgress(wordSetStatsGroupedByWordSet, sourceLanguage));
        wordSetStatsDto.setProgressTarget(calculateProgress(wordSetStatsGroupedByWordSet, targetLanguage));

        return wordSetStatsDto;
    }


    private float calculateProgress(List<WordSetStats> wordSetStatsGroupedByWordSet, String language) {
        List<Set<WordStat>> wordStatSetsListFilteredByLanguage = getWordStatSetsListFilteredByLanguage(wordSetStatsGroupedByWordSet, language);

        WordRateCalculator rateCalculator = new WordRateCalculator(wordStatSetsListFilteredByLanguage);
        Map<Long, Float> wordIdsToRate = rateCalculator.getWordIdsToRate();

        float rateSum = 0f;
        for(Map.Entry<Long, Float> e : wordIdsToRate.entrySet()){
            rateSum += e.getValue();
        }
        return wordIdsToRate.size() == 0 ? 0 : rateSum / wordIdsToRate.size();
    }

    private List<Set<WordStat>> getWordStatSetsListFilteredByLanguage(List<WordSetStats> wordSetStatsGroupedByWordSet, String language) {
        List<Set<WordStat>> wordStatSetsListFilteredByLanguage = wordSetStatsGroupedByWordSet
                .stream()
                .filter(setStats -> setStats.getLanguage().equals(language))
                .map(WordSetStats::getWordStats)
                .collect(Collectors.toList());
        return wordStatSetsListFilteredByLanguage;
    }


    private Map<WordSet, List<WordSetStats>> getWordSet2WordSetStatsListMap(Set<WordSetStats> wordSetStatsForUser) {
        return wordSetStatsForUser.stream()
                .collect(Collectors.groupingBy(WordSetStats::getWordSet));
    }
}
