package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.DTO.OverallStatistics;
import org.arjunaoverdrive.app.DTO.SetStats;
import org.arjunaoverdrive.app.model.Word;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final WordSetService wordSetService;

    @Autowired
    public StatisticsServiceImpl(WordSetService wordSetService) {
        this.wordSetService = wordSetService;
    }


    @Override
    public OverallStatistics getOverallStatistics() {
        OverallStatistics stats = new OverallStatistics();
        List<SetStats> statsList = getSetsStatsList();
        stats.setSetsStatsList(statsList);
        stats.setSetsCount(statsList.size());
        stats.setAvgResults(createAVGResults(statsList));
        return stats;
    }

    private List<SetStats> getSetsStatsList() {
        List<WordSet> wordSetList = wordSetService.findAll();
        List<SetStats> setStats = wordSetList.stream()
                .map(this::buildSetStats)
                .collect(Collectors.toList());
        return setStats;
    }

    private SetStats buildSetStats(WordSet ws) {
        return new SetStats(
                ws.getId(),
                ws.getName(),
                calculateSrcLangProgress(ws),
                ws.getSrcTimesPracticed(),
                getTargetLangProgress(ws),
                ws.getTargetTimesPracticed());
    }

    private double getTargetLangProgress(WordSet ws) {
        List<Word> wordList = ws.getWordList();
        double trgtAccuracy = wordList
                .stream()
                .map(Word::getTrgtRate)
                .reduce(Float::sum)
                .orElse(0f) / wordList.size();

        return trgtAccuracy >= 0.99 ? 1.0 : trgtAccuracy;
    }

    private double calculateSrcLangProgress(WordSet ws) {
        List<Word> wordList = ws.getWordList();
        double srcProgress = wordList
                .stream()
                .map(Word::getSrcRate)
                .reduce(Float::sum)
                .orElse(0f) / wordList.size();

        return srcProgress >= 0.99 ? 1.0 : srcProgress;
    }

    private SetStats createAVGResults(List<SetStats> statsList) {
        SetStats avgResults = new SetStats();

        setProgress(statsList, avgResults);
        setTotalTimesPracticed(statsList, avgResults);
        setTargetTotalTimesPracticed(statsList, avgResults);
        setTargetLangProgress(statsList, avgResults);

        return avgResults;
    }

    private void setProgress(List<SetStats> statsList, SetStats avgResults) {
        int practicedSetsSize = (int) statsList.stream()
                .filter(s -> s.getSrcTimesPracticed() > 0)
                .count();
        double progress = statsList.stream()
                .map(SetStats::getSrcLangProgress)
                .reduce(Double::sum)
                .orElse(0.0) /  practicedSetsSize;

        double res = progress > 0.99 ? 1.0 : progress;
        avgResults.setSrcLangProgress(res);
    }

    private void setTotalTimesPracticed(List<SetStats> statsList, SetStats avgResults) {
        int totalTimesPracticed = statsList.stream()
                .map(SetStats::getSrcTimesPracticed)
                .reduce(Integer::sum)
                .orElse(0);

        avgResults.setSrcTimesPracticed(totalTimesPracticed);
    }

    private void setTargetLangProgress(List<SetStats> statsList, SetStats avgResults) {
        int practicedSetsSize = (int) statsList.stream()
                .filter(s -> s.getTargetTimesPracticed() > 0)
                .count();

        double targetLangProgress = statsList.stream()
                .map(SetStats::getTargetLangProgress)
                .reduce(Double::sum)
                .orElse(0.0) /  practicedSetsSize;
        double res = targetLangProgress > 0.99 ? 1.0 : targetLangProgress;

        avgResults.setTargetLangProgress(res);
    }

    private void setTargetTotalTimesPracticed(List<SetStats> statsList, SetStats avgResults) {
        int targetTotalTimesPracticed = statsList.stream()
                .map(SetStats::getTargetTimesPracticed)
                .reduce(Integer::sum)
                .orElse(0);

        avgResults.setTargetTimesPracticed(targetTotalTimesPracticed);
    }
}
