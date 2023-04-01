package org.arjunaoverdrive.app.services.statistics;

import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.services.statistics.SetResultService;
import org.arjunaoverdrive.app.services.statistics.WordSetStatsService;
import org.arjunaoverdrive.app.services.statistics.WordStatService;
import org.arjunaoverdrive.app.services.user.UserService;
import org.arjunaoverdrive.app.web.DTO.ResultDto;
import org.arjunaoverdrive.app.web.DTO.WordRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SetResultServiceImpl implements SetResultService {

    private final WordSetStatsService setService;
    private final WordStatService wordStatService;
    private final UserService userService;

    @Autowired
    public SetResultServiceImpl(WordSetStatsService setService, WordStatService wordStatService, UserService userService) {
        this.setService = setService;
        this.wordStatService = wordStatService;
        this.userService = userService;
    }

    @Override
    public void save(ResultDto result) {
        Long userId = userService.getUserFromSecurityContext().getId();
        WordSetStats wordSetStats = setService.saveResults(userId, result);
        wordStatService.save(result, wordSetStats);
    }

    @Override
    public void updateWordsRates(List<WordRes> wordResultsList) {

    }
}
