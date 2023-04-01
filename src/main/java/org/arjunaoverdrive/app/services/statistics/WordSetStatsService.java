package org.arjunaoverdrive.app.services.statistics;

import org.arjunaoverdrive.app.model.WordSetStats;
import org.arjunaoverdrive.app.web.DTO.ResultDto;

public interface WordSetStatsService {
    WordSetStats saveResults(Long id, ResultDto result);
}
