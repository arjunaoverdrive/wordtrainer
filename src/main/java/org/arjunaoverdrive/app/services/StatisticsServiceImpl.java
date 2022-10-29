package org.arjunaoverdrive.app.services;

import org.arjunaoverdrive.app.DTO.OverallStatistics;
import org.arjunaoverdrive.app.DTO.SetStats;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final WordSetService wordSetService;
    private final WordService wordService;

    @Autowired
    public StatisticsServiceImpl(WordSetService wordSetService, WordService wordService) {
        this.wordSetService = wordSetService;
        this.wordService = wordService;
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
                ws.getSrcLangAccuracy(),
                ws.getSrcTimesPracticed(),
                ws.getTargetLangAccuracy(),
                ws.getTargetLangTimesPracticed());
    }

    private SetStats createAVGResults(List<SetStats> statsList) {
        SetStats avgResults = new SetStats();

        setAccuracy(statsList, avgResults);
        setTotalTimesPracticed(statsList, avgResults);
        setTargetTotalTimesPracticed(statsList, avgResults);
        setTargetLangAccuracy(statsList, avgResults);

        return avgResults;
    }

    private void setAccuracy(List<SetStats> statsList, SetStats avgResults) {
        Optional<Float> accuracy = statsList.stream()
                .map(SetStats::getSrcLangAccuracy)
                .reduce(Float::sum);
        if (accuracy.isPresent()) {
            avgResults.setSrcLangAccuracy(accuracy.get());
        } else avgResults.setSrcLangAccuracy(0);
    }

    private void setTotalTimesPracticed(List<SetStats> statsList, SetStats avgResults) {
        Optional<Integer> totalTimesPracticed = statsList.stream()
                .map(SetStats::getSrcTimesPracticed)
                .reduce(Integer::sum);
        if (totalTimesPracticed.isPresent()) {
            avgResults.setSrcTimesPracticed(totalTimesPracticed.get());
        } else {
            avgResults.setSrcTimesPracticed(0);
        }
    }

    private void setTargetLangAccuracy(List<SetStats> statsList, SetStats avgResults) {
        Optional<Float> targetLangAccuracy = statsList.stream()
                .map(SetStats::getTargetLangAccuracy)
                .reduce(Float::sum);
        if (targetLangAccuracy.isPresent()) {
            avgResults.setTargetLangAccuracy(targetLangAccuracy.get());
        } else {
            avgResults.setTargetLangAccuracy(0);
        }
    }

    private void setTargetTotalTimesPracticed(List<SetStats> statsList, SetStats avgResults) {
        Optional<Integer> targetTotalTimesPracticed = statsList.stream()
                .map(SetStats::getTargetTimesPracticed)
                .reduce(Integer::sum);
        if (targetTotalTimesPracticed.isPresent()) {
            avgResults.setTargetTimesPracticed(targetTotalTimesPracticed.get());
        } else {
            avgResults.setTargetTimesPracticed(0);
        }
    }
}
