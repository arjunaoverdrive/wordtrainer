package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.model.WordStat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordRateCalculator {
    private final List<Set<WordStat>> wordStats;

    public WordRateCalculator(List<Set<WordStat>> wordStats) {
        this.wordStats = wordStats;
    }

    Map<Long, Float> getWordIdsToRate() {
        Map<Long, Float> idToRate = new HashMap<>();

        for (Set<WordStat> wordStatSet : wordStats) {
            for (WordStat wordStat : wordStatSet) {
                idToRate.compute(wordStat.getWordId(),
                        (k, v) -> v == null ? 1f / wordStat.getRate()
                                : (v += (1f / wordStat.getRate())));
            }
        }
        int count = wordStats.size();

        idToRate.entrySet().forEach(e ->
                e.setValue(count < 7 ?
                        (e.getValue() * ((float) count / (count * 10)))
                        : e.getValue() / count));

        return idToRate;
    }
}
