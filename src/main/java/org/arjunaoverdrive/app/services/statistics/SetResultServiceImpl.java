package org.arjunaoverdrive.app.services.statistics;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.web.dto.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetResultServiceImpl implements SetResultService {

    private final WordSetStatsService setService;
    private final WordStatService wordStatService;


    @Autowired
    public SetResultServiceImpl(WordSetStatsService setService, WordStatService wordStatService) {
        this.setService = setService;
        this.wordStatService = wordStatService;
    }

    @Override
    public void save(ResultDto result, User user) {
        WordSetStats wordSetStats = setService.saveResults(user, result);
        wordStatService.save(result, wordSetStats);
    }

}
