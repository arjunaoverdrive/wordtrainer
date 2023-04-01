package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.DAO.WordSetStatsRepository;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.services.WordSetService;
import org.arjunaoverdrive.app.web.DTO.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class WordSetStatsServiceImpl implements WordSetStatsService {

    private final WordSetStatsRepository wordSetStatsRepository;


    @Autowired
    public WordSetStatsServiceImpl(WordSetStatsRepository wordSetStatsRepository, WordStatService wordStatService, WordSetService wordSetService) {
        this.wordSetStatsRepository = wordSetStatsRepository;

    }


    @Override
    public WordSetStats saveResults(Long userId, ResultDto result) {
        WordSetStats setStats = new WordSetStats();
        setStats.setSetId(result.getSetId());
        setStats.setUserId(userId);
        setStats.setPracticedAt(new Timestamp(System.currentTimeMillis()));
        setStats.setLang(result.isLang() ? 1 : 0);
        setStats.setAccuracy(calculateAccuracy(result));
        return wordSetStatsRepository.save(setStats);
    }

    private float calculateAccuracy(ResultDto result){
        int attemptsCount = result.getResult().stream()
                .map(m -> Integer.parseInt(m.keySet().stream().findFirst().get()))
                .reduce(Integer::sum)
                .get();
        int wordsCount = result.getResult().stream()
                .map(m -> m.values().size())
                .reduce(Integer::sum)
                .get();
        return (float)wordsCount / attemptsCount;
    }

}
