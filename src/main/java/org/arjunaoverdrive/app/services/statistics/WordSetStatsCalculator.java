package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.web.dto.statistics.WordSetDetailedStatsDto;
import org.arjunaoverdrive.app.web.dto.statistics.WordSetStatsDto;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class WordSetStatsCalculator {
    private List<WordSetStats> wordSetStatsGroupedByWordSet;
    private String sourceLanguage;
    private String targetLanguage;

    public WordSetStatsCalculator(List<WordSetStats> wordSetStatsGroupedByWordSet, String sourceLanguage, String targetLanguage) {
        this.wordSetStatsGroupedByWordSet = wordSetStatsGroupedByWordSet;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }

    public WordSetStatsCalculator() {
    }

    public void calculateStatistics(WordSetDetailedStatsDto dto){
        setTimesPracticed(dto);
        setAverageResult(dto);
        setLastPracticed(dto);
        setBestResult(dto);
    }

    private void setTimesPracticed(WordSetDetailedStatsDto dto) {

        dto.setTimesPracticedSource(calculateTimesPracticedForLang(sourceLanguage));
        dto.setTimesPracticedTarget(calculateTimesPracticedForLang(targetLanguage));
    }

    private void setAverageResult(WordSetDetailedStatsDto dto) {
        dto.setAverageResultSourceLang(calculateAverageResultForLang(sourceLanguage));
        dto.setAverageResultTargetLang(calculateAverageResultForLang(targetLanguage));
    }

    private void setLastPracticed(WordSetDetailedStatsDto dto) {
        dto.setLastPracticedSourceLang(getLastPracticedForLang(sourceLanguage));
        dto.setLastPracticedTargetLang(getLastPracticedForLang(targetLanguage));
    }

    private void setBestResult(WordSetDetailedStatsDto dto) {
        dto.setBestResultSourceLang(calculateBestResultForLang(sourceLanguage));
        dto.setBestResultTargetLang(calculateBestResultForLang(targetLanguage));
    }

    private int calculateTimesPracticedForLang(String lang) {
        return (int) wordSetStatsGroupedByWordSet
                .stream()
                .filter(setStats -> setStats.getLanguage().equals(lang))
                .count();
    }


    private float calculateAverageResultForLang(String language) {
        int timesPracticed = calculateTimesPracticedForLang( language);
        return timesPracticed == 0 ? (float) timesPracticed : wordSetStatsGroupedByWordSet.stream()
                .filter(setStats -> setStats.getLanguage().equals(language))
                .map(WordSetStats::getAccuracy)
                .reduce(Float::sum)
                .get() /
                timesPracticed;
    }

    private Timestamp getLastPracticedForLang(String language) {
        int timesPracticed = calculateTimesPracticedForLang(language);
        return timesPracticed == 0 ? null : wordSetStatsGroupedByWordSet
                .stream()
                .filter(setStats -> setStats.getLanguage().equals(language))
                .max(Comparator.comparing(WordSetStats::getPracticedAt))
                .get()
                .getPracticedAt();
    }

    private float calculateBestResultForLang(String language) {
        Optional<WordSetStats> bestResult = wordSetStatsGroupedByWordSet
                .stream()
                .filter(setStats -> setStats.getLanguage().equals(language))
                .max(Comparator.comparing(WordSetStats::getAccuracy));
        return bestResult.isEmpty() ? 0f : bestResult.get().getAccuracy();
    }
}
