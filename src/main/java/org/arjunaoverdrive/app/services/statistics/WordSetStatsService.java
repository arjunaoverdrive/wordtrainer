package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.model.User;
import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.web.dto.ResultDto;
import org.arjunaoverdrive.app.web.dto.statistics.WordSetDetailedStatsDto;

import java.util.Set;

public interface WordSetStatsService {
    WordSetStats saveResults(User id, ResultDto result);

    Set<WordSetStats> getWordSetStatsForUser(User user);

    WordSetDetailedStatsDto getWordSetDetailedStats(Integer id, User user);
}
