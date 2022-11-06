package org.arjunaoverdrive.app.services;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.DTO.OverallStatistics;
import org.arjunaoverdrive.app.DTO.SetStats;
import org.arjunaoverdrive.app.model.WordSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.info("return overall statistics");
        return stats;
    }

    private List<SetStats> getSetsStatsList() {
        List<WordSet> wordSetList = wordSetService.findAll();
        List<SetStats> setStats = wordSetList.stream()
                .map(this::buildSetStats)
                .collect(Collectors.toList());
        log.info("return sets statistics");
        return setStats;
    }

    private SetStats buildSetStats(WordSet ws) {
        return new SetStats(
                ws.getId(),
                ws.getName(),
                ws.getSrcLangAccuracy(),
                ws.getSrcTimesPracticed(),
                ws.getTargetLangAccuracy(),
                ws.getTargetTimesPracticed());
    }

    private SetStats createAVGResults(List<SetStats> statsList) {
        SetStats avgResults = new SetStats();

        setAccuracy(statsList, avgResults);
        setTotalTimesPracticed(statsList, avgResults);
        setTargetTotalTimesPracticed(statsList, avgResults);
        setTargetLangAccuracy(statsList, avgResults);

        log.info("return average results");
        return avgResults;
    }

    private void setAccuracy(List<SetStats> statsList, SetStats avgResults) {
        Optional<Float> accuracy = statsList.stream()
                .map(SetStats::getSrcLangAccuracy)
                .reduce(Float::sum);
        if (accuracy.isPresent()) {
            avgResults.setSrcLangAccuracy(accuracy.get());
            log.info("calculate sets source language average accuracy");
        } else avgResults.setSrcLangAccuracy(0);
    }

    private void setTotalTimesPracticed(List<SetStats> statsList, SetStats avgResults) {
        Optional<Integer> totalTimesPracticed = statsList.stream()
                .map(SetStats::getSrcTimesPracticed)
                .reduce(Integer::sum);
        if (totalTimesPracticed.isPresent()) {
            avgResults.setSrcTimesPracticed(totalTimesPracticed.get());
            log.info("calculate sets source language total times practiced");
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
            log.info("calculate target language average accuracy");
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
            log.info("calculate target language total times practiced");
        } else {
            avgResults.setTargetTimesPracticed(0);
        }
    }
}
